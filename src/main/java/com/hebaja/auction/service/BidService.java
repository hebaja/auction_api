package com.hebaja.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebaja.auction.model.Bid;
import com.hebaja.auction.repository.BidRepository;

@Service
public class BidService {
	
	@Autowired
	private BidRepository repository;
	
	public void save(Bid bid) {
		repository.save(bid);
	}
	
	public List<Bid> findAllByLotId(long lotId) {
		return repository.findAllByLotId(lotId).orElse(null);
	}
	
	public void deleteAll(List<Bid> bids) {
		repository.deleteAll(bids);
	}

	public void delete(Bid bid) {
		repository.delete(bid);
	}

}
