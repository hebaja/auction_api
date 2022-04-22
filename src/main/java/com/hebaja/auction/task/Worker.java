package com.hebaja.auction.task;

import java.math.BigDecimal;

import com.hebaja.auction.enums.BidAnalysisResult;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.Bid;
import com.hebaja.auction.model.Lot;
import com.hebaja.auction.model.Player;
import com.hebaja.auction.service.BidService;
import com.hebaja.auction.service.LotService;

public class Worker {
	
	private final static String TAG = "[Worker] ";
	
	private boolean auctioneerIsAnalisingBid = false;
	
	public BidAnalysisResult playerMakeBid(Auctioneer auctioneer, Player player, Bid bid, Long lotId, BidService bidService, LotService lotService) throws InterruptedException {
		System.out.println(TAG + "is auctioneer analising a bid? " + auctioneerIsAnalisingBid);
		
		synchronized (this) {
			
			while(auctioneerIsAnalisingBid) {
				this.wait();
			}
			
			this.auctioneerIsAnalisingBid = true;
			Lot lot = lotService.findById(lotId);
			BidAnalysisResult result = auctioneer.analiseBid(lot, bid);
			
			switch(result) {
				case BID_VALID:
					bidService.save(bid);
					this.auctioneerIsAnalisingBid = false;
					this.notifyAll();
			    break;
				case BID_GROUP_NOT_ACTIVE:
					this.auctioneerIsAnalisingBid = false;
					this.notifyAll();
			    break;
				case BID_LOT_NOT_STARTED:
					this.auctioneerIsAnalisingBid = false;
					this.notifyAll();
				break;
				case BID_LOWER_OR_EQUAL_THAN_LAST:
					this.auctioneerIsAnalisingBid = false;
					this.notifyAll();
				break;
				case BID_LOWER_THAN_STARTING:
					this.auctioneerIsAnalisingBid = false;
					this.notifyAll();
				break;
			  default:
			    System.out.println(TAG + "unknown result");
			}
			return result;
		}
	}
}
