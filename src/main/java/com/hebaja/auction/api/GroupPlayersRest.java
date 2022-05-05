package com.hebaja.auction.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hebaja.auction.dto.AuctioneerAuctionsDto;
import com.hebaja.auction.dto.GroupPlayerDto;
import com.hebaja.auction.form.ActivateGroupPlayerForm;
import com.hebaja.auction.form.DeleteGroupPlayerForm;
import com.hebaja.auction.form.RegisterGroupPlayerForm;
import com.hebaja.auction.form.UpdateGroupPlayerForm;
import com.hebaja.auction.form.UpdatePlayerForm;
import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.model.Player;
import com.hebaja.auction.service.AuctioneerService;
import com.hebaja.auction.service.GroupPlayerService;
import com.hebaja.auction.service.PlayerService;

@RestController
@CrossOrigin
@RequestMapping("/api/group-player")
public class GroupPlayersRest {
	
	private final static String TAG = GroupPlayersRest.class.toString();

	@Autowired
	private GroupPlayerService groupService;
	
	@Autowired
	private AuctioneerService auctioneerService;
	
	@Autowired
	private PlayerService playerService;
	
	@PostMapping("change-state")
	@CacheEvict(value = "auctioneer-auctions", allEntries = true)
	public ResponseEntity<AuctioneerAuctionsDto> activate(@RequestBody ActivateGroupPlayerForm form) {
		if(form != null) {
			GroupPlayer groupPlayer = groupService.findById(form.getGroupPlayerId());
			changeState(groupPlayer, !form.isGroupActive());
			Auctioneer auctioneer = auctioneerService.findById(form.getAuctioneerId());
			auctioneer.sortAuctions();
			return ResponseEntity.ok(new AuctioneerAuctionsDto(auctioneer));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("register")
	@CacheEvict(value = "auctioneer-auctions", allEntries = true)
	public ResponseEntity<AuctioneerAuctionsDto> register(@RequestBody RegisterGroupPlayerForm form) {
		
		if(form != null) {
			Auctioneer auctioneer = auctioneerService.findById(form.getAuctioneerId());
			List<Player> players = new ArrayList<Player>();
			
			GroupPlayer groupPlayer = new GroupPlayer(form.getName());
			groupPlayer.setAuctioneer(auctioneer);
			
			form.getPlayers().forEach(player -> {
				players.add(new Player(player.getPlayerName(), player.getWalletValue(), groupPlayer));  
			});
			
			groupPlayer.setPlayers(players);
			try {
				groupService.save(groupPlayer);
				auctioneer.sortAuctions();
				return ResponseEntity.ok(new AuctioneerAuctionsDto(auctioneer));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping
	@CacheEvict(value = "auctioneer-auctions", allEntries = true)
	public ResponseEntity<AuctioneerAuctionsDto> update(@RequestBody UpdateGroupPlayerForm form) {
		if(form != null) {
			
			GroupPlayer groupPlayer = groupService.findById(form.getGroupPlayerId());
			Auctioneer auctioneer = auctioneerService.findById(form.getAuctioneerId());
			List<Player> playersToRemove = new ArrayList<Player>();
			
			groupPlayer.getPlayers().forEach(player -> {				
				UpdatePlayerForm updatePlayerForm = form.getPlayers().stream().filter(formPlayer -> formPlayer.getId() == player.getId()).findFirst().orElse(null);
				if(updatePlayerForm != null) {
					player.setName(updatePlayerForm.getPlayerName());
					player.setInitialValueInWallet(updatePlayerForm.getWalletValue());
				} else {
					playersToRemove.add(player);
				}
			});
			
			if(!playersToRemove.isEmpty()) {
				groupPlayer.getPlayers().removeAll(playersToRemove);
			}
			
			form.getPlayers().forEach(formPlayer -> {
				if(formPlayer.getId() == null) {
					Player player = new Player(formPlayer.getPlayerName(), formPlayer.getWalletValue(), groupPlayer);
					playerService.save(player);
					groupPlayer.add(player);
				}
			});
			groupPlayer.setName(form.getGroupPlayerName());
			groupService.save(groupPlayer);
			auctioneer.sortAuctions();
			return ResponseEntity.ok(new AuctioneerAuctionsDto(auctioneer));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("delete")
	@CacheEvict(value = "auctioneer-auctions", allEntries = true)
	public ResponseEntity<AuctioneerAuctionsDto> delete(@RequestBody DeleteGroupPlayerForm form) {
		if(form != null) {
			GroupPlayer groupPlayer = groupService.findById(form.getGroupPlayerId());
			groupService.delete(groupPlayer);
			Auctioneer auctioneer = auctioneerService.findById(form.getAuctioneerId());
			auctioneer.sortAuctions();
			return ResponseEntity.ok(new AuctioneerAuctionsDto(auctioneer));
		}
		return ResponseEntity.badRequest().build();
	}
	
	private void changeState(GroupPlayer groupPlayer, boolean active) {
		groupPlayer.setActive(active);
		groupService.save(groupPlayer);
	}
	
}
