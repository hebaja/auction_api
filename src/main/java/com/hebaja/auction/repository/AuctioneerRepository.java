package com.hebaja.auction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hebaja.auction.model.Auctioneer;

public interface AuctioneerRepository extends JpaRepository<Auctioneer, Long>{

	@Query("select a from Auctioneer a where a.name = :username")
	Optional<Auctioneer> findByUsername(@Param("username") String username);

	@Query("select a from Auctioneer a where a.email = :email")
	Optional<Auctioneer> findByEmail(@Param("email") String email);

}
