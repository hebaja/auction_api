package com.hebaja.auction.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hebaja.auction.dto.GroupPlayerDto;
import com.hebaja.auction.form.ActivateGroupPlayerForm;
import com.hebaja.auction.form.DeleteGroupPlayerForm;
import com.hebaja.auction.form.RegisterGroupPlayerForm;
import com.hebaja.auction.form.UpdateGroupPlayerForm;
import com.hebaja.auction.form.UpdatePlayerForm;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.model.Player;
import com.hebaja.auction.service.AuctioneerService;
import com.hebaja.auction.service.GroupPlayerService;
import com.hebaja.auction.service.PlayerService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class GroupPlayersRest {
	
	private final static String TAG = "[GroupPlayerRest] ";

	@Autowired
	private GroupPlayerService groupService;
	
	@Autowired
	private AuctioneerService auctioneerService;
	
	@Autowired
	private PlayerService playerService;
	
	@PostMapping("group-player/activate")
	@CacheEvict(value = "auctioneer-auctions", allEntries = true)
	public ResponseEntity<List<GroupPlayerDto>> activate(@RequestBody ActivateGroupPlayerForm form) {
		
		try {
			List<GroupPlayer> groupPlayerList = fetchGroupPlayerAndChange(form, true);
			return ResponseEntity.ok(GroupPlayerDto.convertToList(groupPlayerList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.notFound().build();
	}

	@PostMapping("group-player/deactivate")
	@CacheEvict(value = "auctioneer-auctions", allEntries = true)
	public ResponseEntity<List<GroupPlayerDto>> deactivate(@RequestBody ActivateGroupPlayerForm form) {
		
		try {
			List<GroupPlayer> groupPlayerList = fetchGroupPlayerAndChange(form, false);
			return ResponseEntity.ok(GroupPlayerDto.convertToList(groupPlayerList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("group-player/register")
	@CacheEvict(value = "auctioneer-auctions", allEntries = true)
	public ResponseEntity<GroupPlayerDto> register(@RequestBody RegisterGroupPlayerForm form) {
		
		if(form != null) {
			Auctioneer auctioneer = auctioneerService.findById(form.getAuctioneerId());
			List<Player> players = new ArrayList<Player>();
			
			GroupPlayer groupPlayer = new GroupPlayer(form.getName());
			groupPlayer.setAuctioneer(auctioneer);
			
			form.getPlayers().forEach(player -> {
				players.add(new Player(player.getName(), player.getWalletValue(), groupPlayer));  
			});
			
			groupPlayer.setPlayers(players);
			
			try {
				GroupPlayer savedGroupPlayer = groupService.save(groupPlayer);
				return ResponseEntity.ok(GroupPlayerDto.convert(savedGroupPlayer));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("group-player/update")
	@CacheEvict(value = "auctioneer-auctions", allEntries = true)
	public ResponseEntity<GroupPlayerDto> update(@RequestBody UpdateGroupPlayerForm form) {
		if(form != null) {
			
			GroupPlayer groupPlayer = groupService.findById(form.getGroupPlayerId());
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
			GroupPlayer groupPlayerSaved = groupService.save(groupPlayer);
			
			return ResponseEntity.ok(GroupPlayerDto.convert(groupPlayerSaved));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("group-player/delete")
	@CacheEvict(value = "auctioneer-auctions", allEntries = true)
	public ResponseEntity<List<GroupPlayerDto>> delete(@RequestBody DeleteGroupPlayerForm form) {
		if(form != null) {
			GroupPlayer groupPlayer = groupService.findById(form.getGroupPlayerId());
			groupService.delete(groupPlayer);
			Auctioneer auctioneer = auctioneerService.findById(form.getAuctioneerId());
			List<GroupPlayer> groupPlayers = auctioneer.getGroupPlayers();
			return ResponseEntity.ok(GroupPlayerDto.convertToList(groupPlayers));
		}
		return ResponseEntity.notFound().build();
	}
	
	private List<GroupPlayer> fetchGroupPlayerAndChange(ActivateGroupPlayerForm form, boolean active) {
		GroupPlayer groupPlayer = groupService.findById(form.getGroupPlayerId());
		groupPlayer.setActive(active);
		
		groupService.save(groupPlayer);
		List<GroupPlayer> groupPlayerList = groupService.findAllByAuctioneerId(form.getAuctioneerId());
		return groupPlayerList;
	}
	
}
