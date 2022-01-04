package com.hebaja.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebaja.auction.model.Player;
import com.hebaja.auction.repository.PlayerRepository;

@Service
public class PlayerService {
	
	@Autowired
	private PlayerRepository repository;
	
	public Player findById(long id) {
		return repository.findById(id).orElse(null);
	}
	
	public void save(Player player) {
		repository.save(player);
	}
	
	public void saveAll(List<Player> players) {
		repository.saveAll(players);
	}
	
	public void delete(Player player) {
		repository.delete(player);
	}

}
