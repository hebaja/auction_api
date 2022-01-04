package com.hebaja.auction.task;

import java.math.BigDecimal;

import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.Bid;
import com.hebaja.auction.model.Lot;
import com.hebaja.auction.model.Player;
import com.hebaja.auction.service.BidService;
import com.hebaja.auction.service.LotService;

public class Worker {
	
	private final static String TAG = "[Worker] ";
	
	private boolean auctioneerIsAnalisingBid = false;
	
	public Boolean playerMakeBid(Auctioneer auctioneer, Player player, Bid bid, Long lotId, BidService bidService, LotService lotService) throws InterruptedException {
		System.out.println(TAG + "is auctioneer analising a bid? " + auctioneerIsAnalisingBid);
		
		synchronized (this) {
			
			while(auctioneerIsAnalisingBid) {
				System.out.println(TAG + player.getName() + " is waiting...");
				this.wait();
			}
			
			this.auctioneerIsAnalisingBid = true;
			
			System.out.println(TAG + "auctioneer analising is NOW ANALISING a bid");

			Lot lot = lotService.findById(lotId);
						
			Boolean result = auctioneer.analiseBid(lot, bid);
				
			if(result) {
				System.out.println(TAG + player.getName() + " made a valid bid");
				bidService.save(bid);
				this.auctioneerIsAnalisingBid = false;
				this.notifyAll();
				return result;
			}
						
			System.out.println(TAG + player.getName() + " made an INVALID bid");
			
			this.auctioneerIsAnalisingBid = false;
			this.notifyAll();
			
			return result;
			
		}
	}
	
}
