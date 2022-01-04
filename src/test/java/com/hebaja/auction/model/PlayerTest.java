package com.hebaja.auction.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerTest {

    private Player player1;
    private Player player2;
    private Lot lot;

    @BeforeEach
    void init() {
    	Auctioneer auctioneer = new Auctioneer("auctioneer", "test@test.com");
    	GroupPlayer groupPlayer = new GroupPlayer("group_test");
    	groupPlayer.setAuctioneer(auctioneer);
    	
        player1 = new Player("player1", new BigDecimal("200.0"), groupPlayer);
        player2 = new Player("player2", new BigDecimal("100.0"), groupPlayer);
        lot = new Lot();
        lot.setTitle("My Lot");
        lot.setDescription("My lot description");
    }

    @Test
    void playerBidShouldNotBeNullCasePlayerHasEnoughMoneyInWallet() {
        player1.getWallet().setMoney(new BigDecimal("200.0"));
        Bid bid = player1.makeBid(new BigDecimal("100.0"), lot);
        assertNotNull(bid);
    }

    @Test
    void playerBidShouldBeNullCasePlayerHasNotEnoughMoneyInWallet() {
        player2.getWallet().setMoney(new BigDecimal("100.0"));
        Bid bid = player2.makeBid(new BigDecimal("200.0"), lot);
        assertNull(bid);
    }

}
