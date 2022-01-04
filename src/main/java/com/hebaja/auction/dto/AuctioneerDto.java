package com.hebaja.auction.dto;

import com.hebaja.auction.model.Auctioneer;

public class AuctioneerDto {

	private Long id;
	private String name;
	
	public AuctioneerDto(Auctioneer auctioneer) {
		this.id = auctioneer.getId();
		this.name = auctioneer.getName();
	}

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
	
	public static AuctioneerDto convert(Auctioneer auctioneer) {
		return new AuctioneerDto(auctioneer);
	}

}
