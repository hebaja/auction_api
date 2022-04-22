package com.hebaja.auction.form;

public class ActivateGroupPlayerForm {

	private Long auctioneerId;
	private Long groupPlayerId;
	private boolean groupActive;

	public Long getGroupPlayerId() {
		return groupPlayerId;
	}

	public void setGroupPlayerId(Long groupPlayerId) {
		this.groupPlayerId = groupPlayerId;
	}
	
	public Long getAuctioneerId() {
		return auctioneerId;
	}

	public void setAuctioneerId(Long auctioneerId) {
		this.auctioneerId = auctioneerId;
	}

	public boolean isGroupActive() {
		return groupActive;
	}

	public void setGroupActive(boolean groupActive) {
		this.groupActive = groupActive;
	}
	
}
