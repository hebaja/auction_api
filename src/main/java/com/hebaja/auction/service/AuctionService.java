package com.hebaja.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebaja.auction.model.Auction;
import com.hebaja.auction.repository.AuctionRepository;

@Service
public class AuctionService {
	
	@Autowired
	private AuctionRepository repository;
	
	public Auction save(Auction auction) {
		return repository.save(auction);
	}
	
	public Auction findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public List<Auction> findAll() {
		return repository.findAll();
	}
	
	public void delete(Auction auction) {
		repository.delete(auction);
	}

}
