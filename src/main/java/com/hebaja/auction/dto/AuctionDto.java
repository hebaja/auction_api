package com.hebaja.auction.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hebaja.auction.model.Auction;
import com.hebaja.auction.model.GroupPlayer;
import com.hebaja.auction.model.Player;

public class AuctionDto {
	
	private Long id;
	private Long auctioneerId;
	private String title;
	private boolean finished;
	private boolean favorite;
	private List<LotDto> lotsDto;
	private List<PlayerDto> playersDto = new ArrayList<PlayerDto>();

	public AuctionDto(Auction auction, List<Player> players) {
		this.id = auction.getId();
		this.auctioneerId = auction.getAuctioneer().getId();
		this.setTitle(auction.getTitle());
		this.finished = auction.isFinished();
		this.favorite = auction.isFavorite();
		this.lotsDto = auction.getLots().stream().map(LotDto::new).collect(Collectors.toList());
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
	
}
