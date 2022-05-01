package com.hebaja.auction.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.model.Player;

public class AuctioneerAuctionsDto {
	
	private final String TAG = "[AuctioneerAuctionsDto] "; 
	
	private Long id;
	private String name;
	private String pitureUrl;
	private List<AuctionDto> auctionsDto;
	private List<AuctionDto> favoriteAuctionsDto;
	private List<GroupPlayerDto> groupPlayerDto;
	
	public AuctioneerAuctionsDto(Auctioneer auctioneer, List<Auction> auctions, List<Auction> favoriteAuctions) {
		this.setId(auctioneer.getId());
		this.setName(auctioneer.getName());
		this.pitureUrl = auctioneer.getPictureUrl();
		if(auctioneer.getAuctions() != null) {
			ArrayList<AuctionDto> auctionDtoList = new ArrayList<AuctionDto>();
			ArrayList<AuctionDto> favoriteAuctionDtoList = new ArrayList<AuctionDto>();
			
			auctions.forEach(auction -> {
				if(auctionIsFinished(auctioneer, auction)) {
					fetchFinishedAuction(auctionDtoList, auction, auctioneer);
				} else {
					auctionDtoList.add(new AuctionDto(auction, auctioneer));
				}
			});
			
			favoriteAuctions.forEach(auction -> {
				if(auctionIsFinished(auctioneer, auction)) {
					fetchFinishedAuction(favoriteAuctionDtoList, auction, auctioneer);
				} else {
					favoriteAuctionDtoList.add(new AuctionDto(auction, auctioneer));
				}
			});
			
			this.setAuctionsDto(auctionDtoList);
			this.setFavoriteAuctionsDto(favoriteAuctionDtoList);
		}
		if(auctioneer.getGroupPlayers() != null) {
			Collections.sort(auctioneer.getGroupPlayers(), (group, otherGroup) -> group.getName().compareTo(otherGroup.getName()));
			this.setGroupPlayerDto(auctioneer.getGroupPlayers().stream().map(GroupPlayerDto::new).collect(Collectors.toList()));	
		}
	}

	private boolean auctionIsFinished(Auctioneer auctioneer, Auction auction) {
		return auctioneer.getFinishedAuctionsIds().contains(auction.getId());
	}
	
	private void fetchFinishedAuction(ArrayList<AuctionDto> auctionDtoList, Auction auction, Auctioneer auctioneer) {
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
		auctionDtoList.add(new AuctionDto(auction, auctioneer, players));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonProperty("auctions")
	public List<AuctionDto> getAuctionsDto() {
		return auctionsDto;
	}

	public void setAuctionsDto(List<AuctionDto> auctionsDto) {
		this.auctionsDto = auctionsDto;
	}
	
	@JsonProperty("groupPlayers")
	public List<GroupPlayerDto> getGroupPlayerDto() {
		return groupPlayerDto;
	}

	public void setGroupPlayerDto(List<GroupPlayerDto> groupPlayerDto) {
		this.groupPlayerDto = groupPlayerDto;
	}
	
	@JsonProperty("favoriteAuctions")
	public List<AuctionDto> getFavoriteAuctionsDto() {
		return favoriteAuctionsDto;
	}

	public void setFavoriteAuctionsDto(List<AuctionDto> favoriteAuctionsDto) {
		this.favoriteAuctionsDto = favoriteAuctionsDto;
	}

	public String getPitureUrl() {
		return pitureUrl;
	}

	public void setPitureUrl(String pitureUrl) {
		this.pitureUrl = pitureUrl;
	}
}
