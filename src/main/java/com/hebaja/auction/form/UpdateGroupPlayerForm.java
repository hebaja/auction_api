package com.hebaja.auction.form;

import java.util.List;

public class UpdateGroupPlayerForm {

	private Long groupPlayerId;
	private String groupPlayerName;
	private Long auctioneerId;
	private List<UpdatePlayerForm> players;
	
	public Long getAuctioneerId() {
		return auctioneerId;
	}

	public void setAuctioneerId(Long auctioneerId) {
		this.auctioneerId = auctioneerId;
	}

	public List<UpdatePlayerForm> getPlayers() {
		return players;
	}

	public void setPlayers(List<UpdatePlayerForm> players) {
		this.players = players;
	}

	public Long getGroupPlayerId() {
		return groupPlayerId;
	}

	public void setGroupPlayerId(Long groupPlayerId) {
		this.groupPlayerId = groupPlayerId;
	}

	public String getGroupPlayerName() {
		return this.groupPlayerName;
	}

	public void setGroupPlayerName(String groupPlayerName) {
		this.groupPlayerName = groupPlayerName;
	}

}
