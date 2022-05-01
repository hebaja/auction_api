package com.hebaja.auction.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.UpdateRequest;
import com.hebaja.auction.dto.AuctioneerAuctionsDto;
import com.hebaja.auction.dto.AuctioneerDto;
import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.service.AuctioneerService;

@RestController
@CrossOrigin
@RequestMapping("/api/firebase-auth")
public class FirebaseAuthRest {
	
	private static final String TAG = FirebaseAuthRest.class.toString();
	
	@Autowired
	private AuctioneerService service;
	
	@PostMapping
	@Cacheable(value = "auctioneer-auctions")
	public ResponseEntity<AuctioneerDto> auth(@RequestBody String idToken) throws FirebaseAuthException {
		
		FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
		
		String email = decodedToken.getEmail();
		String[] emailSplit = email.split("@");
		String name = emailSplit[0];
						
		Auctioneer auctioneer = service.findByEmail(decodedToken.getEmail());
		
		if(auctioneer != null) {
			Collections.sort(auctioneer.getAuctions());
			auctioneer.getAuctions().forEach(auction -> {
				Collections.sort(auction.getLots());
			});
			return ResponseEntity.ok(new AuctioneerDto(auctioneer));
		} else {
			Auctioneer newAuctioneer = new Auctioneer(decodedToken.getName(), decodedToken.getEmail());
			newAuctioneer.setAuctions(new ArrayList<Auction>());
			newAuctioneer.setGroupPlayers(new ArrayList<GroupPlayer>());
			if(decodedToken.getPicture() != null) {
				newAuctioneer.setPictureUrl(decodedToken.getPicture());
			}
			try {
				return ResponseEntity.ok(new AuctioneerDto(service.save(newAuctioneer)));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(TAG + " " + e.getMessage());
				return ResponseEntity.internalServerError().build();
			}
		}
	}
	
	@PostMapping("facebook-verify-email")
	@Cacheable(value = "auctioneer-auctions")
	public ResponseEntity<String> facebookVerifyEmail(@RequestBody String idToken) throws FirebaseAuthException {
		
		System.out.println(idToken);
		
		FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
		
		Boolean emailIsVerified = (Boolean) decodedToken.getClaims().get("email_verified");
		
		Map<String, String> firebaseClaimsMap = (Map<String, String>) decodedToken.getClaims().get("firebase");
		String provider = firebaseClaimsMap.get("sign_in_provider");
		
		
		
		System.out.println(emailIsVerified);
		System.out.println(provider);
		System.out.println(provider.equals("facebook.com"));
		
		
		if(!emailIsVerified && provider.equals("facebook.com")) {
			UpdateRequest request = new UserRecord.UpdateRequest(decodedToken.getUid())
			.setEmailVerified(true);
			
			UserRecord userRecord = FirebaseAuth.getInstance().updateUser(request);
			System.out.println("Successfully updated user: " + userRecord.getUid());
			
			

			
		}
		
		
	
		
		
		return ResponseEntity.ok().build();
		
		
	}
}
