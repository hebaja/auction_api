package com.hebaja.auction;

import java.math.BigDecimal;
import java.util.Arrays;

import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.model.Lot;
import com.hebaja.auction.model.Player;

public class SimulateGame {

    public static void main(String[] args) {
    	
        Auctioneer auctioneer = new Auctioneer("auctioneer", "test@test.com");

        Lot lot1 = makeLot("Asset 1", "This is asset 1");
        Lot lot2 = makeLot("Asset 2", "This is asset 2");
        Lot lot3 = makeLot("Asset 3", "This is asset 3");
        Lot lot4 = makeLot("Asset 4", "This is asset 4");
        
        Auction auction = new Auction("test_auction");
        auction.setAuctioneer(auctioneer);
        
        lot1.setAuction(auction);
        lot2.setAuction(auction);
        lot3.setAuction(auction);
        lot4.setAuction(auction);
        
        auction.setLots(Arrays.asList(lot1, lot2, lot3, lot4));
        
        auctioneer.setAuctions(Arrays.asList(auction));

        GroupPlayer group = new GroupPlayer("test_group");
        group.setAuctioneer(auctioneer);
        Player player1 = new Player("player1", new BigDecimal("500.0"), group);
        Player player2 = new Player("player2", new BigDecimal("500.0"), group);
        group.add(player1);
        group.add(player2);

        
        auctioneer.analiseBid(lot1, player1.makeBid(new BigDecimal("60.0"), lot1));
        
        auctioneer.startLot(lot1, new BigDecimal("50.0"));
        auctioneer.startLot(lot2, new BigDecimal("50.0"));
        auctioneer.startLot(lot3, new BigDecimal("50.0"));
        auctioneer.startLot(lot4, new BigDecimal("50.0"));

        auctioneer.analiseBid(lot1, player1.makeBid(new BigDecimal("49.0"), lot1));
        
        auctioneer.activateGroup(group);
        
        auctioneer.analiseBid(lot1, player1.makeBid(new BigDecimal("49.0"), lot1));
        auctioneer.analiseBid(lot1, player1.makeBid(new BigDecimal("60.0"), lot1));
        auctioneer.analiseBid(lot1, player2.makeBid(new BigDecimal("70.0"), lot1));
        auctioneer.analiseBid(lot1, player1.makeBid(new BigDecimal("67.0"), lot1));
        auctioneer.analiseBid(lot1, player1.makeBid(new BigDecimal("75.0"), lot1));
        auctioneer.analiseBid(lot1, player1.makeBid(new BigDecimal("80.0"), lot1));
        auctioneer.analiseBid(lot1, player2.makeBid(new BigDecimal("85.0"), lot1));
        
        auctioneer.analiseBid(lot2, player1.makeBid(new BigDecimal("60.0"), lot2));
        auctioneer.analiseBid(lot2, player2.makeBid(new BigDecimal("70.0"), lot2));
        auctioneer.analiseBid(lot2, player1.makeBid(new BigDecimal("67.0"), lot2));

        auctioneer.analiseBid(lot3, player1.makeBid(new BigDecimal("60.0"), lot3));
        auctioneer.analiseBid(lot3, player2.makeBid(new BigDecimal("70.0"), lot3));
        auctioneer.analiseBid(lot3, player1.makeBid(new BigDecimal("67.0"), lot3));
        auctioneer.analiseBid(lot3, player1.makeBid(new BigDecimal("75.0"), lot3));
        auctioneer.analiseBid(lot3, player1.makeBid(new BigDecimal("80.0"), lot3));
        auctioneer.analiseBid(lot3, player2.makeBid(new BigDecimal("85.0"), lot3));
        auctioneer.analiseBid(lot3, player1.makeBid(new BigDecimal("85.0"), lot3));
        auctioneer.analiseBid(lot3, player1.makeBid(new BigDecimal("90.0"), lot3));

        auctioneer.analiseBid(lot4, player1.makeBid(new BigDecimal("60.0"), lot4));
        auctioneer.analiseBid(lot4, player2.makeBid(new BigDecimal("70.0"), lot4));
        auctioneer.analiseBid(lot4, player1.makeBid(new BigDecimal("67.0"), lot4));
        auctioneer.analiseBid(lot4, player1.makeBid(new BigDecimal("75.0"), lot4));
        auctioneer.analiseBid(lot4, player1.makeBid(new BigDecimal("80.0"), lot4));
        auctioneer.analiseBid(lot4, player2.makeBid(new BigDecimal("85.0"), lot4));
        auctioneer.analiseBid(lot4, player1.makeBid(new BigDecimal("90.0"), lot4));
        auctioneer.analiseBid(lot4, player2.makeBid(new BigDecimal("92.0"), lot4));

        auctioneer.finishLot(lot1);
        auctioneer.finishLot(lot2);
        auctioneer.finishLot(lot3);
        auctioneer.finishLot(lot4);

        System.out.println();
        System.out.println("-----------------------------------");
        player1.getAcquiredLots().forEach(lot -> System.out.println("player1 has asset: " + lot.getTitle() + ". And paid " + lot.getPricePaid() + " for it."));
        System.out.println("now player1 has " + player1.getWallet().getMoney() + " in wallet");

        System.out.println();
        System.out.println("-----------------------------------");
        player2.getAcquiredLots().forEach(lot -> System.out.println("player2 has asset: " + lot.getTitle() + ". And paid " + lot.getPricePaid() + " for it."));
        System.out.println("now player2 has " + player2.getWallet().getMoney() + " in wallet");

    }

    private static Lot makeLot(String title, String description) {
    	return new Lot(title, description);
    }

}
