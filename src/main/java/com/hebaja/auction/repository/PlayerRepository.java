package com.hebaja.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hebaja.auction.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long>{
	
}
