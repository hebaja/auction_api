package com.hebaja.auction.form;

import java.util.List;

public class RegisterAuctionForm {

	private Long auctioneerId;
	private String title;
	private List<LotForm> lots;

	public Long getAuctioneerId() {
		return auctioneerId;
	}

	public void setAuctioneerId(Long auctioneerId) {
		this.auctioneerId = auctioneerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<LotForm> getLots() {
		return lots;
	}

	public void setLots(List<LotForm> lots) {
		this.lots = lots;
	}

}
