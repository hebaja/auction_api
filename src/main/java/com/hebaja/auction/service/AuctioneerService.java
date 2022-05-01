package com.hebaja.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.repository.AuctionRepository;
import com.hebaja.auction.repository.AuctioneerRepository;

@Service
public class AuctioneerService {
	
	@Autowired
	private AuctioneerRepository auctioneerRepository;
	
	@Autowired
	private AuctionRepository auctionRepository;
	
	public Auctioneer findById(long id) {
		return auctioneerRepository.findById(id).orElse(null);
	}
	
	public Auctioneer save(Auctioneer auctioneer) {
		return auctioneerRepository.save(auctioneer);
	}

	public Auctioneer findByUsername(String username) {
		return auctioneerRepository.findByUsername(username).orElse(null);
	}
	
	public Auctioneer findByEmail(String email) {
		return auctioneerRepository.findByEmail(email).orElse(null);
	}

	public void delete(Auctioneer auctioneer) {
		auctioneerRepository.delete(auctioneer);
	}

	public List<Auctioneer> findAll() {
		return auctioneerRepository.findAll();
	}
	
	public List<Auction> findFavoriteAuctions(List<Long> ids) {
		return auctionRepository.findAllById(ids);
	}

}
