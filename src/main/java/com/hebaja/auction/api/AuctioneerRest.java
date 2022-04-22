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
@RequestMapping("/api/auctioneer")
public class AuctioneerRest {
	
	private static final String TAG = AuctioneerRest.class.toString();
	
	@Autowired
	private AuctioneerService service;
	
	@GetMapping("auctions/{auctioneerId}")
	@Cacheable(value = "auctioneer-auctions")
	public ResponseEntity<AuctioneerAuctionsDto> auctioneerId(@PathVariable("auctioneerId") Long auctioneerId) {
		if(auctioneerId != null) {
			Auctioneer auctioneer = service.findById(auctioneerId);
//			Collections.sort(auctioneer.getAuctions());
//			auctioneer.getAuctions().forEach(auction -> {
//				Collections.sort(auction.getLots());
//			});
			auctioneer.sortAuctions();
			return ResponseEntity.ok(AuctioneerAuctionsDto.convert(auctioneer));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("auctions/email/{email}")
	@Cacheable(value = "auctioneer-auctions")
	public ResponseEntity<AuctioneerAuctionsDto> auctioneerEmail(@PathVariable("email") String email) {
		if(email != null) {
			Auctioneer auctioneer = service.findByEmail(email);
			return ResponseEntity.ok(AuctioneerAuctionsDto.convert(auctioneer));
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("{id}")
	public AuctioneerDto auctioneer(@PathVariable("id") Long id) {
		Auctioneer auctioneer = service.findById(id);
		return AuctioneerDto.convert(auctioneer);
	}
	
	@PostMapping("delete")
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
