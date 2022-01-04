package com.hebaja.auction.form;

import java.math.BigDecimal;

public class UpdatePlayerForm {

	private Long id;
	private String playerName;
	private BigDecimal walletValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public BigDecimal getWalletValue() {
		return walletValue;
	}

	public void setWalletValue(BigDecimal walletValue) {
		this.walletValue = walletValue;
	}

}
