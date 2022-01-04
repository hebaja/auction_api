package com.hebaja.auction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hebaja.auction.model.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {

	@Query("select b from Bid b where b.lot.id = :lot_id")
	Optional<List<Bid>> findAllByLotId(@Param("lot_id") long lotId);
	
}
