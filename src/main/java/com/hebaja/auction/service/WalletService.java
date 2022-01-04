package com.hebaja.auction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hebaja.auction.model.Wallet;
import com.hebaja.auction.repository.WalletRepository;

@Service
public class WalletService {
	
	@Autowired
	private WalletRepository repository;
	
	public void save(Wallet wallet) {
		repository.save(wallet);
	}

}
