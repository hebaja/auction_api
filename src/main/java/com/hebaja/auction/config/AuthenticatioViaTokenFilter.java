package com.hebaja.auction.config;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.repository.AuctioneerRepository;

public class AuthenticatioViaTokenFilter extends OncePerRequestFilter {
	
	private static final String TAG = AuthenticatioViaTokenFilter.class.toString();

	private AuctioneerRepository auctioneerRepository;

	public AuthenticatioViaTokenFilter(AuctioneerRepository auctioneerRepository) {
		this.auctioneerRepository = auctioneerRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recoverToken(request);
		
		if(token != null) {
			try {
				FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
				if(decodedToken != null) {
					if(decodedToken.isEmailVerified()) {
						authenticateClient(decodedToken);
					} else {
						Map<String, String> firebaseClaimsMap = (Map<String, String>) decodedToken.getClaims().get("firebase");
						String provider = firebaseClaimsMap.get("sign_in_provider");
						if(provider.equals("facebook.com")) {
							UpdateRequest updateRequest = new UserRecord.UpdateRequest(decodedToken.getUid())
								.setEmailVerified(true);
								FirebaseAuth.getInstance().updateUser(updateRequest);
								authenticateClient(decodedToken);
						} else {
							System.out.println(TAG + " E-mail is not verified");
						}
					}
				}
			} catch (FirebaseAuthException e) {
				System.out.println(e.getMessage());
			}
		}
		
		filterChain.doFilter(request, response);
	}

	private void authenticateClient(FirebaseToken decodedToken) {
		Auctioneer user = auctioneerRepository.findByEmail(decodedToken.getEmail()).orElse(null);
		if(user != null) {
			authenticate(user);
		}
		
	}

	private void authenticate(Auctioneer user) {
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}
}
