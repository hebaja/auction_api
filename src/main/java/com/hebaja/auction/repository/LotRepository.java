package com.hebaja.auction.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Lot;

public interface LotRepository extends JpaRepository<Lot, Long> {

	@Query("select l from Lot l where l.auction = :auction and l.active = true")
	Lot findActiveLotFromAuctioneer(@Param("auction")Auction auction);
	
//	@Query("select l from Lot l where l.auctioneer.id = :auctioneerId")
//	List<Lot> findAllByAuctioneerId(@Param("auctioneeId") long auctioneerId);
	
}
