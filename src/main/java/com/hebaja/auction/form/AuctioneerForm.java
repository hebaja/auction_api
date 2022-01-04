package com.hebaja.auction.form;

import com.hebaja.auction.model.Auctioneer;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AuctioneerForm {

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Auctioneer convert() {
		Auctioneer auctioneer = new Auctioneer();
		
//		String passwordHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray());
		
		auctioneer.setName(name);
		return auctioneer;
	}

}
