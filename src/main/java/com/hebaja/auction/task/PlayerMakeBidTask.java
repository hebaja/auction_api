package com.hebaja.auction.task;

import java.util.concurrent.Callable;

import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.Bid;
import com.hebaja.auction.model.Player;
import com.hebaja.auction.service.BidService;
import com.hebaja.auction.service.LotService;

public class PlayerMakeBidTask implements Callable<Boolean> {

	private Worker worker;
	private Player player;
	private Auctioneer auctioneer;
	private Bid bid;
	private BidService bidService;
	private Long lotId;
	private LotService lotService;
	
	public PlayerMakeBidTask(Worker worker, Auctioneer auctioneer, Player player, Bid bid, Long lotId, BidService bidService, LotService lotService) {
		this.worker = worker;
		this.auctioneer = auctioneer;
		this.player = player;
		this.bid = bid;
		this.lotId = lotId;
		this.lotService = lotService;
		this.bidService = bidService;
	}

	@Override
	public Boolean call() throws Exception {
		return worker.playerMakeBid(auctioneer, player, bid, lotId, bidService, lotService);
	}
	
	

}
