package com.hebaja.auction.dto;

import java.math.BigDecimal;

import com.hebaja.auction.model.Bid;

public class BidDto {

	private Long id;
	private Long playerId;
	private String playerName;
	private BigDecimal value;
	
	public BidDto(Bid bid) {
		this.id = bid.getId();
		this.playerId = bid.getPlayer().getId();
		this.playerName = bid.getPlayer().getName();
		this.value = bid.getValue();
	}
	
	public static BidDto convert(Bid bid) {
		return new BidDto(bid);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

}
