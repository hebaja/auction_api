package com.hebaja.auction.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hebaja.auction.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
