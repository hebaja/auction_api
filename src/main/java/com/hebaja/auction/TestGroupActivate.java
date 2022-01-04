package com.hebaja.auction;

import java.math.BigDecimal;
import java.util.Arrays;

import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.model.Lot;
import com.hebaja.auction.model.Player;

public class TestGroupActivate {
	
	public static void main(String[] args) {
		
		Auctioneer auctioneer1 = new Auctioneer("auctioneer1", "test1@test.com");
		Auctioneer auctioneer2 = new Auctioneer("auctioneer2", "test2@test.com");
		
		GroupPlayer group1 = new GroupPlayer("group1");
		GroupPlayer group2 = new GroupPlayer("group2");
		
		Player player1 = new Player("player1", new BigDecimal("500.0"), group1);
		Player player2 = new Player("player2", new BigDecimal("500.0"), group2);
		
		group1.add(player1);
		group2.add(player2);
		group1.setAuctioneer(auctioneer1);
		group2.setAuctioneer(auctioneer2);
		
		auctioneer1.setGroupPlayers(Arrays.asList(group1));
		auctioneer2.setGroupPlayers(Arrays.asList(group2));
		
		Auction auction1 = new Auction("auction1");
		Auction auction2 = new Auction("auction2");
		Auction auction3 = new Auction("auction3");
		
		auction1.setAuctioneer(auctioneer1);
		auction2.setAuctioneer(auctioneer2);
		auction3.setAuctioneer(auctioneer1);
		
		Lot lot1 = new Lot("asset 1", "description 1");
		Lot lot2 = new Lot("asset 2", "description 2");
		Lot lot3 = new Lot("asset 3", "description 3");
		
		lot1.setAuction(auction1);
		lot2.setAuction(auction2);
		lot3.setAuction(auction3);
		
		auctioneer1.startLot(lot1, new BigDecimal("50.0"));
		System.out.println(lot1.getTitle() + " is active? " + lot1.isActive());
		auctioneer2.startLot(lot2, new BigDecimal("50.0"));
		System.out.println(lot2.getTitle() + " is active? " + lot2.isActive());
		auctioneer2.startLot(lot3, new BigDecimal("50.0"));
		System.out.println(lot3.getTitle() + " is active? " + lot3.isActive());
		
		
		
	}

}
