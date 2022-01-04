package com.hebaja.auction.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuctioneerTest {

    private Auctioneer auctioneer;
    private GroupPlayer group;
    private Player player1;
    private Player player2;
    private Lot lot;

    @BeforeEach
    void init() {
        this.auctioneer = new Auctioneer("auctioneer", "test@test.com");
        Auction auction = new Auction("test_auction");
        auction.setAuctioneer(auctioneer);
//        Asset asset = new Asset("MyAsset", "Test Asset");
        lot = new Lot("My Lot", "My lot description");
        
        List<Lot> lots = new ArrayList<>();
        lots.add(lot);
                
        auction.setLots(lots);
        
        List<Auction> auctions = new ArrayList<>();
        auctions.add(auction);
        
        this.auctioneer.setAuctions(auctions);
        
        group = new GroupPlayer("group_test");
        
        group.setAuctioneer(auctioneer);
                
        this.player1 = new Player("player1", new BigDecimal("500.0"), group);
        this.player2 = new Player("player2", new BigDecimal("600.0"), group);
        
        group.add(player1);
        group.add(player2);
        
        this.lot = this.auctioneer.getAuctions().get(0).getLots().get(0);
        this.lot.setAuction(auction);
    }

    @Test
    void shouldDenyPlayerBidCaseItIsLowerThanPreviousBid() {
    	this.auctioneer.startLot(lot, new BigDecimal("50.0"));
    	this.auctioneer.activateGroup(group);
    	
    	Bid firstBid = player2.makeBid(new BigDecimal("150.0"), lot);
    	
    	if(firstBid != null) {
    		Boolean result = auctioneer.analiseBid(lot, firstBid);
    		assertTrue(result);
    	} else {
    		fail();
    	}
    	
        Bid secondBid = player1.makeBid(new BigDecimal("100.0"), lot);

        if (secondBid != null) {
            Boolean result = auctioneer.analiseBid(lot, secondBid);
            assertFalse(result);
        } else {
            fail();
        }
    }

    @Test
    void shouldAcceptPlayerBidCaseItIsHigherThanPreviousBid() {
    	this.auctioneer.startLot(lot, new BigDecimal("50.0"));
    	this.auctioneer.activateGroup(group);
    	
    	Bid firstBid = player2.makeBid(new BigDecimal("150.0"), lot);
    	
    	if(firstBid != null) {
    		Boolean result = auctioneer.analiseBid(lot, firstBid);
    		assertTrue(result);
    	} else {
    		fail();
    	}
    	
        Bid secondBid = player1.makeBid(new BigDecimal("200.00"), lot);

        if (secondBid != null) {
            Boolean result = auctioneer.analiseBid(lot, secondBid);
            assertTrue(result);
        } else {
            fail();
        }
    }

    @Test
    void shouldDenyPlayerBidCaseItEqualsPreviousBid() {
    	this.auctioneer.startLot(lot, new BigDecimal("50.0"));
    	this.auctioneer.activateGroup(group);
    	
    	Bid firstBid = player2.makeBid(new BigDecimal("100.0"), lot);
    	
    	if(firstBid != null) {
    		Boolean result = auctioneer.analiseBid(lot, firstBid);
    		assertTrue(result);
    	} else {
    		fail();
    	}
    	
        Bid bid = player1.makeBid(new BigDecimal("100.00"), lot);

        if (bid != null) {
            Boolean result = auctioneer.analiseBid(lot, bid);
            assertFalse(result);
        } else {
            fail();
        }
    }

    @Test
    void shouldEndTestWithCorrectNumbersOfBidsWithCorrectValues() {
    	this.auctioneer.startLot(lot, new BigDecimal("50.0"));
    	this.auctioneer.activateGroup(group);
    	this.auctioneer.analiseBid(lot, player1.makeBid(new BigDecimal("40.0"), lot));
    	this.auctioneer.analiseBid(lot, player1.makeBid(new BigDecimal("100.0"), lot));
    	this.auctioneer.analiseBid(lot, player2.makeBid(new BigDecimal("150.0"), lot));
    	this.auctioneer.analiseBid(lot, player1.makeBid(new BigDecimal("200.0"), lot));
    	this.auctioneer.analiseBid(lot, player2.makeBid(new BigDecimal("190.0"), lot));
    	this.auctioneer.analiseBid(lot, player2.makeBid(new BigDecimal("250.0"), lot));
    	this.auctioneer.analiseBid(lot, player1.makeBid(new BigDecimal("300.0"), lot));

        assertEquals(lot.getBids().size(), 5);
        assertEquals(lot.getBids().get(0).getValue(), new BigDecimal("100.0"));
        assertEquals(lot.getBids().get(1).getValue(), new BigDecimal("150.0"));
        assertEquals(lot.getBids().get(2).getValue(), new BigDecimal("200.0"));
        assertEquals(lot.getBids().get(3).getValue(), new BigDecimal("250.0"));
        assertEquals(lot.getBids().get(4).getValue(), new BigDecimal("300.0"));
    }

    @Test
    void shouldFinalizeLotAndPassAssetToPlayerThatGaveTheHighestBid() {
    	this.auctioneer.startLot(lot, new BigDecimal("50.0"));
    	auctioneer.activateGroup(group);
    	this.auctioneer.analiseBid(lot, player1.makeBid(new BigDecimal("100.0"), lot));
    	this.auctioneer.analiseBid(lot, player2.makeBid(new BigDecimal("200.0"), lot));
    	this.auctioneer.analiseBid(lot, player1.makeBid(new BigDecimal("300.0"), lot));
    	this.auctioneer.analiseBid(lot, player2.makeBid(new BigDecimal("400.0"), lot));

    	this.auctioneer.finishLot(lot);

        assertFalse(lot.isActive());
        assertEquals(player2.getAcquiredLots().get(0).getTitle(), "My Lot");
        assertEquals(player2.getAcquiredLots().get(0).getDescription(), "My lot description");
        assertEquals(player2.getAcquiredLots().get(0).getPricePaid(), new BigDecimal("400.0"));
        assertEquals(player2.getWallet().getMoney(), new BigDecimal("200.0"));

    }

    @Test
    void shouldNotAcceptBidCaseLotIsFinalized() {
    	this.auctioneer.startLot(lot, new BigDecimal("50.0"));
    	this.auctioneer.activateGroup(group);
    	this.auctioneer.analiseBid(lot, player1.makeBid(new BigDecimal("100.0"), lot));
    	this.auctioneer.analiseBid(lot, player2.makeBid(new BigDecimal("200.0"), lot));
    	this.auctioneer.analiseBid(lot, player1.makeBid(new BigDecimal("300.0"), lot));
    	this.auctioneer.analiseBid(lot, player2.makeBid(new BigDecimal("400.0"), lot));

        assertEquals(lot.getBids().size(), 4);

        this.auctioneer.finishLot(this.lot);

        Boolean result = auctioneer.analiseBid(lot, player1.makeBid(new BigDecimal("450.0"), lot));

        assertFalse(result);
        assertEquals(lot.getBids().size(), 4);

    }

    @Test
    void shouldNotAcceptPlayerBidCaseAuctioneerHasNotStartedLot() {
        Bid bid = player1.makeBid(new BigDecimal("100.0"), lot);
        Boolean result = auctioneer.analiseBid(lot, bid);
        assertFalse(result);
    }

    @Test
    void lotShouldStartInactive() {
        Boolean result = lot.isActive();
        assertFalse(result);
    }
    
    @Test
    void shouldStartLotIfItBelongsToAuctioneer() {
    	assertFalse(lot.isActive());
    	auctioneer.startLot(lot, new BigDecimal("50.0"));
    	assertTrue(lot.isActive());
    }

    @Test
    void shouldActivateGroupIfItBelongsToAuctioneer() {
    	assertFalse(group.isActive());
    	auctioneer.activateGroup(group);
    	assertTrue(group.isActive());
    }
    
    @Test
    void shouldNotActivateGroupIfItDoesNotBelongToAuctioneer() {
    	Auctioneer anotherAuctioneer = new Auctioneer("another_auctioneer", "anothertest@test.com");
    	GroupPlayer anotherGroup = new GroupPlayer("another group");
    	anotherGroup.setAuctioneer(anotherAuctioneer);
    	assertFalse(anotherGroup.isActive());
    	auctioneer.activateGroup(anotherGroup);
    	assertFalse(anotherGroup.isActive());
    }
    
}
