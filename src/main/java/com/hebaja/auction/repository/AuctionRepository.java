package com.hebaja.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hebaja.auction.model.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long>{

}
