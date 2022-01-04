package com.hebaja.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.Lot;
import com.hebaja.auction.repository.LotRepository;

@Service
public class LotService {

    @Autowired
    private LotRepository repository;

    public Lot save(Lot lot) {
        return repository.save(lot);
    }

    public void saveAll(List<Lot> lots) {
    	repository.saveAll(lots);
        
    }

    public List<Lot> findAll() {
        return repository.findAll();
    }
    
    public List<Lot> findAllByAuctioneerId(long id) {
    	return findAllByAuctioneerId(id);
    }
    
    public void delete(Lot lot) {
    	repository.delete(lot);
    }

	public Lot findById(Long lotId) {
		return repository.findById(lotId).orElse(null);
	}
	
	public Lot findActiveLotFromAuctioneer(Auction auction) {
		return repository.findActiveLotFromAuctioneer(auction);
	}

	public void deleteAll(List<Lot> lots) {
		repository.deleteAll(lots);
	}

}
