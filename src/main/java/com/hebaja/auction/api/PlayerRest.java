package com.hebaja.auction.api;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hebaja.auction.dto.PlayerDto;
import com.hebaja.auction.enums.BidAnalysisResult;
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
	
	private static final String TAG = PlayerRest.class.toString();

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private AuctioneerService auctioneerService;
	
	@Autowired
	private LotService lotService;
	
	@Autowired
	private BidService bidService;
	
	private Worker worker = new Worker(); 
	
	@GetMapping("{id}")
	@Cacheable(value = "player")
	public ResponseEntity<PlayerDto> playerId(@PathVariable("id") Long id) {
		Player player = playerService.findById(id);
		
		if(player != null) {
			
			Auctioneer auctioneer = auctioneerService.findById(player.getGroupPlayers().getAuctioneer().getId());
			Auction activeAuction = fetchActiveAuction(auctioneer);
			
			if(activeAuction != null) {
				Lot activeLot = activeAuction.getLots()
						.stream()
							.filter(lot -> lot.isActive())
							.findFirst().orElse(null);
				if(activeLot != null) {
					return ResponseEntity.ok(PlayerDto.convertWithActiveLot(player, activeLot));
				}
			}
			return ResponseEntity.ok(PlayerDto.convert(player));
		}
		System.out.println("returning 404");
		return ResponseEntity.notFound().build();
	}

	private Auction fetchActiveAuction(Auctioneer auctioneer) {
		return auctioneer
				.getAuctions()
				.stream()
					.filter(auction -> auction.getLots().stream()
					.anyMatch(lot -> lot.isActive()))
					.findFirst().orElse(fetchFavoriteActiveAuction(auctioneer));
	}

	private Auction fetchFavoriteActiveAuction(Auctioneer auctioneer) {
		return auctioneerService
					.findFavoriteAuctions(auctioneer.getFavoriteAuctionsId())
					.stream()
						.filter(auction -> auction.getLots().stream()
						.anyMatch(lot -> lot.isActive()))
						.findFirst().orElse(null);
	}
	
	@PostMapping("make-bid")
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<PlayerDto> makeBid(@RequestBody MakeBidForm form) throws InterruptedException, ExecutionException {
		if(form != null) {
			Player player = playerService.findById(form.getPlayerId());
			if(player.getGroupPlayers().isActive()) {
				Auctioneer auctioneer = auctioneerService.findByUsername(player.getGroupPlayers().getAuctioneer().getName());
				Lot lot = lotService.findById(form.getLotId());
				Bid bid = player.makeBid(form.getValue(), lot);
				
				ExecutorService threadPool = ThreadPoolService.getThreadPool();
				
				Future<BidAnalysisResult> futureResult = threadPool.submit(new PlayerMakeBidTask(worker, auctioneer, player, bid, form.getLotId(), bidService, lotService));
				
				if(futureResult.get() != null) {
					switch (futureResult.get()) {
					case BID_VALID:
						lot.getBids().add(bid);
						return ResponseEntity.ok(PlayerDto.convertWithActiveLot(player, lot));
					case BID_LOWER_OR_EQUAL_THAN_LAST:
						return ResponseEntity.status(HttpStatus.CONFLICT).build();
					case BID_LOWER_THAN_STARTING:
						return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
					case BID_GROUP_NOT_ACTIVE:
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
					case BID_LOT_NOT_STARTED: 
						return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
					default:
						return ResponseEntity.badRequest().build();
					}
				}
				
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			
			
			
		}
		return ResponseEntity.notFound().build();
	}

}