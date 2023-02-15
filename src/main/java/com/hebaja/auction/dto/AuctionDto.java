package com.hebaja.auction.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.Player;

public class AuctionDto {
	
	private Long id;
	private Long auctioneerId;
	private String title;
	private boolean finished;
	private boolean favorite;
	private boolean openLot;
	private boolean publicAuction;
	private String creationDate;
	private List<LotDto> lotsDto;
	private List<PlayerDto> playersDto = new ArrayList<PlayerDto>();
	
	public AuctionDto(Auction auction) {
		this.id = auction.getId();
		this.auctioneerId = auction.getAuctioneer().getId();
		this.setTitle(auction.getTitle());
		this.finished = auction.isFinished();
		this.openLot = auction.getLots().stream().anyMatch(lot -> lot.isActive());
		this.lotsDto = auction.getLots().stream().map(LotDto::new).collect(Collectors.toList());
		this.publicAuction = auction.isPublicAuction();
		this.creationDate = auction.getCreationDate().toString();
	}

	public AuctionDto(Auction auction, List<Player> players) {
		this.id = auction.getId();
		this.auctioneerId = auction.getAuctioneer().getId();
		this.setTitle(auction.getTitle());
		this.finished = auction.isFinished();
		this.openLot = auction.getLots().stream().anyMatch(lot -> lot.isActive());
		this.publicAuction = auction.isPublicAuction();
		this.lotsDto = auction.getLots().stream().map(LotDto::new).collect(Collectors.toList());
		this.creationDate = auction.getCreationDate().toString();
		if(players != null) {
			players.forEach(player -> {
				PlayerDto playerDto = PlayerDto.convert(player);
				playersDto.add(playerDto);
			});
		}
	}
	
	public AuctionDto(Auction auction, Auctioneer auctioneer) {
		this.id = auction.getId();
		this.auctioneerId = auction.getAuctioneer().getId();
		this.setTitle(auction.getTitle());
		this.finished = auction.isFinished();
		this.openLot = auction.getLots().stream().anyMatch(lot -> lot.isActive());
		this.favorite = auction.isFavorite();
		this.publicAuction = auction.isPublicAuction();
		this.lotsDto = auction.getLots().stream().map(LotDto::new).collect(Collectors.toList());
		this.creationDate = auction.getCreationDate().toString();
	}
	
	public AuctionDto(Auction auction, Auctioneer auctioneer, List<Player> players) {
		this.id = auction.getId();
		this.auctioneerId = auctioneer.getId();
		this.setTitle(auction.getTitle());
		this.finished = auction.isFinished();
		this.openLot = auction.getLots().stream().anyMatch(lot -> lot.isActive());
		this.favorite = auction.isFavorite();
		this.publicAuction = auction.isPublicAuction();
		this.lotsDto = auction.getLots().stream().map(LotDto::new).collect(Collectors.toList());
		this.creationDate = auction.getCreationDate().toString();
		if(players != null) {
			players.forEach(player -> {
				PlayerDto playerDto = PlayerDto.convert(player);
				playersDto.add(playerDto);
			});
		}
	}
	
	public static AuctionDto convert(Auction auction, List<Player> players) {
		return new AuctionDto(auction, players);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@JsonProperty("lots")
	public List<LotDto> getLotsDto() {
		return lotsDto;
	}

	public void setLotsDto(List<LotDto> lotsDto) {
		this.lotsDto = lotsDto;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	@JsonProperty("players")
	public List<PlayerDto> getPlayersDto() {
		return playersDto;
	}

	public void setPlayersDto(List<PlayerDto> playersDto) {
		this.playersDto = playersDto;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public Long getAuctioneerId() {
		return auctioneerId;
	}

	public void setAuctioneerId(Long auctioneerId) {
		this.auctioneerId = auctioneerId;
	}

	public boolean isPublicAuction() {
		return publicAuction;
	}

	public void setPublicAuction(boolean publicAuction) {
		this.publicAuction = publicAuction;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isOpenLot() {
		return openLot;
	}

	public void setOpenLot(boolean openLot) {
		this.openLot = openLot;
	}
	
}
