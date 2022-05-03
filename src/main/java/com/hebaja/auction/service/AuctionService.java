package com.hebaja.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<Auction> findAll(Pageable pagination) {
		return repository.findAll(pagination);
	}
	
	public Page<Auction> findAllPublic(Pageable pagination) {
		return repository.findAllPublic(pagination);
	}
	
	public void delete(Auction auction) {
		repository.delete(auction);
	}
	
	public Page<Auction> findByTitleLike(String like, Pageable pagination) {
		return repository.findByTitleLikeIgnoreCase(like, pagination);
	}

}
