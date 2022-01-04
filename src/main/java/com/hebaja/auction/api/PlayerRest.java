package com.hebaja.auction.api;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hebaja.auction.dto.PlayerDto;
import com.hebaja.auction.form.MakeBidForm;
import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.Bid;
import com.hebaja.auction.model.Lot;
import com.hebaja.auction.model.Player;
import com.hebaja.auction.service.AuctioneerService;
import com.hebaja.auction.service.BidService;
import com.hebaja.auction.service.LotService;
import com.hebaja.auction.service.PlayerService;
import com.hebaja.auction.task.PlayerMakeBidTask;
import com.hebaja.auction.task.ThreadPoolService;
import com.hebaja.auction.task.Worker;

@RestController
@CrossOrigin
@RequestMapping("/api/player")
public class PlayerRest {
	
	private static final String TAG = "[PlayerRest] ";

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private AuctioneerService auctioneerService;
	
	@Autowired
	private LotService lotService;
	
	@Autowired
	private BidService bidService;
	
	private Lot activeLot;
	
	private Worker worker = new Worker(); 
	
	@GetMapping("{id}")
	@Cacheable(value = "player")
	public ResponseEntity<PlayerDto> playerId(@PathVariable("id") Long id) {
		Player player = playerService.findById(id);
		
		if(player != null) {
			String auctioneerName = player.getGroupPlayers().getAuctioneer().getName();
			Auctioneer auctioneer = auctioneerService.findByUsername(auctioneerName);
			
			Auction activeAuction = auctioneer.getAuctions()
					.stream()
						.filter(auction -> auction.getLots().stream()
						.anyMatch(lot -> lot.isActive()))
						.findFirst().orElse(null);
			
			if(activeAuction != null) {
				activeLot = activeAuction.getLots()
						.stream()
							.filter(lot -> lot.isActive())
							.findFirst().orElse(null);
			} else {
				activeLot = null;
			}
			
			if(this.activeLot != null) {
				return ResponseEntity.ok(PlayerDto.convertWithActiveLot(player, this.activeLot));
			} else {
				return ResponseEntity.ok(PlayerDto.convert(player));
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("make-bid")
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<PlayerDto> makeBid(@RequestBody MakeBidForm form) throws InterruptedException, ExecutionException {
		
		if(form != null) {
		
			Player player = playerService.findById(form.getPlayerId());
			Auctioneer auctioneer = auctioneerService.findByUsername(player.getGroupPlayers().getAuctioneer().getName());
			Lot lot = lotService.findById(form.getLotId());
			Bid bid = player.makeBid(form.getValue(), lot);
			
			System.out.println(TAG + "worker -> " + worker);
			if(lot != null) {
				System.out.println(TAG + "lot -> " + lot.getTitle());
			}
			
			ExecutorService threadPool = ThreadPoolService.getThreadPool();
			
			Future<Boolean> futureResult = threadPool.submit(new PlayerMakeBidTask(worker, auctioneer, player, bid, form.getLotId(), bidService, lotService));
			
			if(futureResult.get() != null) {
				if(futureResult.get()) {
					return ResponseEntity.ok(PlayerDto.convertWithActiveLot(player, lot));
				} else {
					return ResponseEntity.badRequest().build();
				}
				
			}
			
			
		}
		return ResponseEntity.notFound().build();
	}
	

}