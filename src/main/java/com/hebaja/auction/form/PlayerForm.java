package com.hebaja.auction.form;

import java.math.BigDecimal;

public class PlayerForm {

	private String name;
	private BigDecimal walletValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getWalletValue() {
		return walletValue;
	}

	public void setWalletValue(BigDecimal walletValue) {
		this.walletValue = walletValue;
	}

}
