package com.hebaja.auction.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hebaja.auction.model.Auction;

public interface AuctionRepository extends JpaRepository<Auction, Long>{
	
	@Query("select a from Auction a where a.publicAuction = true")
	Page<Auction> findAllPublic(Pageable pageable);

	Page<Auction> findByTitleLikeIgnoreCase(String like, Pageable pagination);

}
