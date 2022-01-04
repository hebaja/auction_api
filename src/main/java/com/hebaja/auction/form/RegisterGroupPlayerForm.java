package com.hebaja.auction.form;

import java.util.List;

public class RegisterGroupPlayerForm {

	private String name;
	private Long auctioneerId;
	private List<PlayerForm> players;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAuctioneerId() {
		return auctioneerId;
	}

	public void setAuctioneerId(Long auctioneerId) {
		this.auctioneerId = auctioneerId;
	}

	public List<PlayerForm> getPlayers() {
		return players;
	}

	public void setPlayers(List<PlayerForm> players) {
		this.players = players;
	}

}
