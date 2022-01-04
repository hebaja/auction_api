package com.hebaja.auction.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hebaja.auction.model.GroupPlayer;

public interface GroupPlayerRepository extends JpaRepository<GroupPlayer, Long> {
	
	@Query("select g from GroupPlayer g where g.auctioneer.id = :auctioneerId")
	List<GroupPlayer> findAllByAuctioneerId(@Param("auctioneerId") Long auctioneerId);

}
