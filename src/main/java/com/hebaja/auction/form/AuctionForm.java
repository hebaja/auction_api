package com.hebaja.auction.form;

import java.util.List;

public class AuctionForm {

	private Long id;
	private String title;
	private List<LotForm> lots;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
