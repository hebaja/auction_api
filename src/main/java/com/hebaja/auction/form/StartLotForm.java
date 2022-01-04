package com.hebaja.auction.form;

import java.math.BigDecimal;

public class StartLotForm {

	private Long auctioneerId;
	private Long lotId;
	private BigDecimal startingBid;

	public Long getAuctioneerId() {
		return auctioneerId;
	}

	public void setAuctioneerId(Long auctioneerId) {
		this.auctioneerId = auctioneerId;
	}

	public Long getLotId() {
		return lotId;
	}

	public void setLotId(Long lotId) {
		this.lotId = lotId;
	}

	public BigDecimal getStartingBid() {
		return startingBid;
	}

	public void setStartingBid(BigDecimal startingBid) {
		this.startingBid = startingBid;
	}
	
}
