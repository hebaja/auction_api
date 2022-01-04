//package com.hebaja.auction.task;
//
//import java.util.concurrent.Callable;
//
//import com.hebaja.auction.model.Auctioneer;
//
//public class AuctioneerAnaliseBidTask implements Runnable {
//	
//	private final static String TAG = "[AuctioneerAnaliseBidTask] ";
//	
//	private Worker worker;
//	private Auctioneer auctioneer;
//
//	public AuctioneerAnaliseBidTask(Worker worker, Auctioneer auctioneer) {
//		this.worker = worker;
//		this.auctioneer = auctioneer;
//	}
//
//	@Override
//	public void run() {
//		while(true) {
//			try {
//				worker.auctioneerAnaliseBid(auctioneer);
//			} catch (InterruptedException e) {
//				throw new RuntimeException(e);
//			}	
//		}
//			
//	}
//
//	
//		
//		
//	
//
//	
//	
//	
//
//}
