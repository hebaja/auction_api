package com.hebaja.auction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.repository.GroupPlayerRepository;

@Service
public class GroupPlayerService {
	
	@Autowired
	private GroupPlayerRepository repository;
	
	public GroupPlayer save(GroupPlayer group) {
		return repository.save(group);
	}
	
	public List<GroupPlayer> findAllByAuctioneerId(Long id) {
		return repository.findAllByAuctioneerId(id);
	}
	
	public GroupPlayer findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public void delete(GroupPlayer groupPlayer) {
		repository.delete(groupPlayer);
	}

}
