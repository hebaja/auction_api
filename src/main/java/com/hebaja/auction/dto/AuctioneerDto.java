package com.hebaja.auction.dto;

import com.hebaja.auction.model.Auctioneer;

public class AuctioneerDto {

	private Long id;
	private String name;
	private String email;
	private String pictureUrl;
	
	public AuctioneerDto(Auctioneer auctioneer) {
		this.id = auctioneer.getId();
		this.name = auctioneer.getName();
		this.setEmail(auctioneer.getEmail());
		this.pictureUrl = auctioneer.getPictureUrl();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

}
