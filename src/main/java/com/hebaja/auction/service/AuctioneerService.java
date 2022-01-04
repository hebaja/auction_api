package com.hebaja.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.repository.AuctioneerRepository;

@Service
public class AuctioneerService {
	
	@Autowired
	private AuctioneerRepository repository;
	
	public Auctioneer findById(long id) {
		return repository.findById(id).orElse(null);
	}
	
	public Auctioneer save(Auctioneer auctioneer) {
		return repository.save(auctioneer);
	}

	public Auctioneer findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}
	
	public Auctioneer findByEmail(String email) {
		return repository.findByEmail(email).orElse(null);
	}

	public void delete(Auctioneer auctioneer) {
		repository.delete(auctioneer);
	}

}
