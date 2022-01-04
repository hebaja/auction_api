package com.hebaja.auction.api;

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

import com.google.firebase.auth.FirebaseAuth;
import com.hebaja.auction.dto.AuctioneerAuctionsDto;
import com.hebaja.auction.dto.AuctioneerDto;
import com.hebaja.auction.form.AuctioneerForm;
import com.hebaja.auction.form.DeleteAuctioneerForm;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.service.AuctioneerService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuctioneerRest {
	
	private final String TAG = "[AuctioneerRest] ";
	
	@Autowired
	private AuctioneerService service;
	
	@GetMapping("auctioneer-auctions/{id}")
	@Cacheable(value = "auctioneer-auctions")
	public ResponseEntity<AuctioneerAuctionsDto> auctioneerId(@PathVariable("id") Long id) {
		if(id != null) {
			Auctioneer auctioneer = service.findById(id);
			
			Collections.sort(auctioneer.getAuctions());
			
			auctioneer.getAuctions().forEach(auction -> {
				Collections.sort(auction.getLots());
			});
			
			return ResponseEntity.ok(AuctioneerAuctionsDto.convert(auctioneer));
		}
		return ResponseEntity.notFound().build();
		
	}
	
	@GetMapping("auctioneer-auctions/email/{email}")
	@Cacheable(value = "auctioneer-auctions")
	public ResponseEntity<AuctioneerAuctionsDto> auctioneerEmail(@PathVariable("email") String email) {
		if(email != null) {
			Auctioneer auctioneer = service.findByEmail(email);
			return ResponseEntity.ok(AuctioneerAuctionsDto.convert(auctioneer));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("auctioneer/{id}")
	public AuctioneerDto auctioneer(@PathVariable("id") Long id) {
		Auctioneer auctioneer = service.findById(id);
		return AuctioneerDto.convert(auctioneer);
	}
	
	@PostMapping("auctioneer/delete")
	public ResponseEntity<?> deleteAuctioneer(@RequestBody DeleteAuctioneerForm form) {
		if(form != null) {
			Auctioneer auctioneer = service.findById(form.getId());
			try {
				service.delete(auctioneer);
				FirebaseAuth.getInstance().deleteUser(form.getUid());
				return ResponseEntity.ok("User successfully");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return ResponseEntity.notFound().build();
	}
	
	
}
