//package com.hebaja.auction.task;
//
//import java.util.concurrent.Callable;
//
//import com.hebaja.auction.model.Auctioneer;
//import com.hebaja.auction.model.Bid;
//import com.hebaja.auction.model.Lot;
//
//public class AnaliseBidTask implements Callable<Boolean> {
//	
//	private final String TAG = "[AnaliseBidTask] "; 
//
//	private Auctioneer auctioneer;
//	private Lot lot;
//	private Bid bid;
//	
//	public AnaliseBidTask(Auctioneer auctioneer, Lot lot, Bid bid) {
//		this.auctioneer = auctioneer;
//		this.lot = lot;
//		this.bid = bid;
//	}
//
//	@Override
//	public Boolean call() throws Exception {
//		
//		Boolean result;
//		
//		synchronized (auctioneer) {
//			
//			System.out.println(TAG + " is auctioneer free? " + analisingBid);
//			
//			while(analisingBid) {
//				System.out.println(TAG + bid.getPlayer().getName() + " is waiting");
//				auctioneer.wait();
//				System.out.println(TAG + bid.getPlayer().getName() + " is waiting...");
//				
//			}
//			
//			analisingBid = true;
//			
//			System.out.println(TAG + auctioneer.getName() + " is analising a bid");
//			
//			System.out.println(TAG + "thread name -> " + Thread.currentThread().getName());
//			System.out.println(TAG + "player name -> " + bid.getPlayer().getName());
//			System.out.println(TAG + "bid value -> " + bid.getValue());
//			result = auctioneer.analiseBid(lot, bid);
//			Thread.sleep(7000);
//			System.out.println(TAG + "is bid valid? -> " + result);
//			
////			analisingBid = false;
//			
//			System.out.println(TAG + auctioneer.getName() + " is waiting for new bids");
//			
//		}
//			
//		
//		return result;
//	}
//	
//}
