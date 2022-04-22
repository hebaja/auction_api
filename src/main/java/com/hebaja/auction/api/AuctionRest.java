package com.hebaja.auction.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hebaja.auction.dto.AuctionDto;
import com.hebaja.auction.dto.AuctioneerAuctionsDto;
import com.hebaja.auction.dto.LotDto;
import com.hebaja.auction.dto.PlayerDto;
import com.hebaja.auction.form.AuctionForm;
import com.hebaja.auction.form.FinishLotForm;
import com.hebaja.auction.form.LotForm;
import com.hebaja.auction.form.RegisterAuctionForm;
import com.hebaja.auction.form.StartLotForm;
import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.model.Lot;
import com.hebaja.auction.model.Player;
import com.hebaja.auction.service.AuctionService;
import com.hebaja.auction.service.AuctioneerService;
import com.hebaja.auction.service.BidService;
import com.hebaja.auction.service.GroupPlayerService;
import com.hebaja.auction.service.LotService;
import com.hebaja.auction.service.PlayerService;

@RestController
@CrossOrigin
@RequestMapping("/api/auction")
public class AuctionRest {
	
	private static final String TAG = AuctionRest.class.toString();
	
	@Autowired
	private AuctioneerService auctioneerService; 
	
	@Autowired
	private LotService lotService;
	
	@Autowired
	private AuctionService auctionService;
	
	@Autowired
	private BidService bidService;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private GroupPlayerService groupPlayerService;
	
	@GetMapping("{auctionId}")
	public ResponseEntity<AuctionDto> auction(@PathVariable("auctionId") Long auctionId) {
		if(auctionId != null) {
			Auction auction = auctionService.findById(auctionId);
			if(auction.isFinished()) {
				List<Player> players = fetchPlayersForAuction(auction);
				auction.setLots(auction.getLots().stream().distinct().collect(Collectors.toList()));
				return ResponseEntity.ok(new AuctionDto(auction, players));
			} else {
				auction.setLots(auction.getLots().stream().distinct().collect(Collectors.toList()));
				return ResponseEntity.ok(new AuctionDto(auction, null));
			}
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("lot/{lotId}")
	public ResponseEntity<LotDto> lot(@PathVariable("lotId") Long lotId) {
		if(lotId != null) {
			Lot lot = lotService.findById(lotId);
			return ResponseEntity.ok(new LotDto(lot));
		} 
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping("register")
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<AuctionDto> register(@RequestBody RegisterAuctionForm form) {
		System.out.println(form.getTitle());
		Auctioneer auctioneer = auctioneerService.findById(form.getAuctioneerId());
		Auction auction = new Auction(form.getTitle());
		List<Lot> lots = new ArrayList<Lot>();
		form.getLots().forEach(formLot -> {
			lots.add(new Lot(formLot.getTitle(), formLot.getDescription(), formLot.isCorrect(), auction));
		});
		auction.setLots(lots);
		auction.setAuctioneer(auctioneer);
		Auction savedAuction = auctionService.save(auction);
		return ResponseEntity.ok(AuctionDto.convert(savedAuction, null));
	}
	
	@PutMapping
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<AuctionDto> update(@RequestBody AuctionForm form) {
		
		if(form != null) {
			Auction auction = auctionService.findById(form.getId());
			
			List<Lot> lotsToRemove = new ArrayList<Lot>();
			auction.getLots().forEach(lot -> {
				LotForm lotForm = form.getLots().stream().filter(aForm -> aForm.getId() == lot.getId()).findFirst().orElse(null);
				if(lotForm != null) {
					lot.setTitle(lotForm.getTitle());
					lot.setDescription(lotForm.getDescription());
					lot.setCorrect(lotForm.isCorrect());
				} else {
					lotsToRemove.add(lot);
				}
			});
			
			if(!lotsToRemove.isEmpty()) {
				auction.getLots().removeAll(lotsToRemove);
				lotService.deleteAll(lotsToRemove);
			}
			
			form.getLots().forEach(lotForm -> {
				if(lotForm.getId() == null) {
					Lot lot = new Lot(lotForm.getTitle(), lotForm.getDescription(), lotForm.isCorrect(), auction);
					lotService.save(lot);
					auction.getLots().add(lot);
				}
			});
			
			auction.setTitle(form.getTitle());
			Auction savedAuction = auctionService.save(auction);
			
			return ResponseEntity.ok(AuctionDto.convert(savedAuction, null));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("finish")
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<AuctionDto> finish(@RequestBody AuctionForm form) {
		if(form != null) {
			Auction auction = auctionService.findById(form.getId());
			auction.setFinished(true);
			try {
				Auction finishedAuction = auctionService.save(auction);
				
				List<Player> players = fetchPlayersForAuction(finishedAuction);
				
				return ResponseEntity.ok(AuctionDto.convert(finishedAuction, players));
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	private List<Player> fetchPlayersForAuction(Auction auction) {
		List<GroupPlayer> groupsWithBidsInAuciton = new ArrayList<GroupPlayer>();
		List<Player> players = new ArrayList<Player>();
		
		auction.getLots().forEach(lot -> {
			lot.getBids().forEach(bid -> {
				groupsWithBidsInAuciton.add(bid.getPlayer().getGroupPlayers());
			});
		});
		
		List<GroupPlayer> groupsWithBidsInAucitonFiltered = groupsWithBidsInAuciton.stream().distinct().collect(Collectors.toList());
		
		groupsWithBidsInAucitonFiltered.forEach(groupPlayer -> {
			groupPlayer.getPlayers().forEach(player -> {
				player.getAcquiredLots().forEach(lot -> {
					if(lot.isCorrect()) {
						player.incrementScore();
					}
				});
				players.add(player);
			});
		});
		
		Collections.sort(players, (player, otherPlayer) -> Integer.compare(otherPlayer.getScore(), player.getScore()));
		List<Lot> lotListWithoutDuplicates = auction.getLots().stream().distinct().collect(Collectors.toList());
		auction.setLots(lotListWithoutDuplicates);
		return players;
	}

	@PostMapping("start-lot")
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<LotDto> startLot(@RequestBody StartLotForm form) {
		try {
			Lot lot = lotService.findById(form.getLotId());
			Auctioneer auctioneer = auctioneerService.findById(lot.getAuction().getAuctioneer().getId());
			auctioneer.startLot(lot, form.getStartingBid());
			
			auctioneer.getAuctions().forEach(auction -> {
				auction.setLots(auction.getLots().stream().distinct().collect(Collectors.toList()));
			});
			
			Lot savedLot = lotService.save(lot);
			
			auctioneer.getAuctions().forEach(auction -> {
				Collections.sort(auction.getLots());
			});
			
			if(savedLot != null) {
				auctioneer.getAuctions().forEach(auction -> {
					auction.getLots().forEach(aLot -> {
					});
				});	
			}
			return ResponseEntity.ok(LotDto.convert(savedLot));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("finish-lot")
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<AuctionDto> finishLot(@RequestBody FinishLotForm form) {
		if(form != null) {
			Lot lot = lotService.findById(form.getLotId());
			Auctioneer auctioneer = lot.getAuction().getAuctioneer();
			Lot lotFinished = auctioneer.finishLot(lot);
			if(lotFinished != null) {
				lotService.save(lotFinished);
				auctioneer.getAuctions().forEach(auction -> {
					auction.setLots(auction.getLots().stream().distinct().collect(Collectors.toList()));
					Collections.sort(auction.getLots());
				});
				return ResponseEntity.ok(new AuctionDto(lot.getAuction(), null));
			} else {
				return ResponseEntity.badRequest().build();
			}
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("reset-lot/{lotId}")
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<AuctionDto> resetLot(@PathVariable("lotId") Long lotId) {
		if(lotId != null) {
			Lot lot = lotService.findById(lotId);
									
			if(lot != null) {
				lot.setPricePaid(null);
				lot.setActive(false);
				lot.setStartingBid(null);
				lot.getBids().forEach(bid -> bidService.delete(bid));
				lot.getBids().clear();
				Lot lotChanged = lotService.save(lot);
				
				return ResponseEntity.ok(new AuctionDto(lotChanged.getAuction(), null));
			} else {
				return ResponseEntity.badRequest().build();
			}
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping("reset/{auctionId}")
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<AuctionDto> reset(@PathVariable("auctionId") Long auctionId) {
		if(auctionId != null) {
			Auction auction = auctionService.findById(auctionId);
			try {
				auction.getLots().forEach(lot -> {
					if(lot.getBids().size() > 0) {
						Player player = lot.getBids().get(lot.getBids().size() -1).getPlayer();
						player.setAcquiredLots(new ArrayList<Lot>());
						playerService.save(player);
					}
					lot.setPricePaid(null);
					lot.setActive(false);
					lot.setStartingBid(null);
					lot.getBids().forEach(bid -> bidService.delete(bid));
					lot.getBids().clear();
					lotService.save(lot);
				});
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.internalServerError().build();
			}
			auction.setFinished(false);
			Auction auctionReset = auctionService.save(auction);
			
			List<Lot> lotsWithoutDuplicates = auctionReset.getLots().stream().distinct().collect(Collectors.toList());
			auctionReset.setLots(lotsWithoutDuplicates);
			
			return ResponseEntity.ok(AuctionDto.convert(auctionReset, null));
			
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("delete")
	@CacheEvict(value = {"auctioneer-auctions", "player"}, allEntries = true)
	public ResponseEntity<AuctioneerAuctionsDto> delete(@RequestBody AuctionForm form) {
		if(form != null) {
			try {
				Auction auctionToBeRemoved = auctionService.findById(form.getId());
				auctionService.delete(auctionToBeRemoved);
				
				Long auctioneerId = auctionToBeRemoved.getAuctioneer().getId();
				
				Auctioneer auctioneer = auctioneerService.findById(auctioneerId);
				auctioneer.sortAuctions();
				return ResponseEntity.ok(AuctioneerAuctionsDto.convert(auctioneer));
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.badRequest().build();
			}
		}
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("players-result/{auctionId}")
	public ResponseEntity<List<PlayerDto>> playersResult(@PathVariable("auctionId") Long id) {
		if(id != null) {
			
			Auction auction = auctionService.findById(id);
			
			List<GroupPlayer> groupsWithBidsInAuciton = new ArrayList<GroupPlayer>();
			List<Player> players = new ArrayList<Player>();
			
			auction.getLots().forEach(lot -> {
				lot.getBids().forEach(bid -> {
					groupsWithBidsInAuciton.add(bid.getPlayer().getGroupPlayers());
				});
			});
			
			List<GroupPlayer> groupsWithBidsInAucitonFiltered = groupsWithBidsInAuciton.stream().distinct().collect(Collectors.toList());
			
			groupsWithBidsInAucitonFiltered.forEach(groupPlayer -> {
				groupPlayer.getPlayers().forEach(player -> {
					player.getAcquiredLots().forEach(lot -> {
						if(lot.isCorrect()) {
							player.incrementScore();
						}
					});
					players.add(player);
				});

			});
			
			Collections.sort(players, (player, otherPlayer) -> Integer.compare(otherPlayer.getScore(), player.getScore()));
			
			return ResponseEntity.ok(PlayerDto.convertToList(players));
		}
		return ResponseEntity.notFound().build();
	}
	
}
