package com.hebaja.auction.form;

import java.math.BigDecimal;

public class PlayerForm {

	private String playerName;
	private BigDecimal walletValue;

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
