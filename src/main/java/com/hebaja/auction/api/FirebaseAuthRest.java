package com.hebaja.auction.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.hebaja.auction.config.FirebaseConfig;
import com.hebaja.auction.dto.AuctioneerAuctionsDto;
import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.service.AuctioneerService;

@RestController
@CrossOrigin
@RequestMapping("/api/firebase-auth")
public class FirebaseAuthRest {
	
	private final String TAG = "[FirebaseAuthRest] ";
	
	@Autowired
	private AuctioneerService service;
	
	@PostMapping
	@Cacheable(value = "auctioneer-auctions")
	public ResponseEntity<AuctioneerAuctionsDto> auth(@RequestBody String idToken) throws FirebaseAuthException {
		
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
			
			AuctioneerAuctionsDto dto = AuctioneerAuctionsDto.convert(auctioneer);
			return ResponseEntity.ok(dto);
		} else {
			Auctioneer newAuctioneer = new Auctioneer(name, decodedToken.getEmail());
			newAuctioneer.setAuctions(new ArrayList<Auction>());
			newAuctioneer.setGroupPlayers(new ArrayList<GroupPlayer>());
			try {
				Auctioneer savedAuctioneer = service.save(newAuctioneer);
				AuctioneerAuctionsDto dto = AuctioneerAuctionsDto.convert(savedAuctioneer);
				return ResponseEntity.ok(dto);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("FirebaseAuthRest ---> " + e.getMessage());
				return ResponseEntity.internalServerError().build();
			}
		}
	}
	
	@GetMapping("configure")
	public ResponseEntity<String> configure() {
		FirebaseConfig firebase = new FirebaseConfig();
		try {
			firebase.configure();
			return ResponseEntity.ok("Firebase started with name: " + FirebaseApp.DEFAULT_APP_NAME);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	

}
