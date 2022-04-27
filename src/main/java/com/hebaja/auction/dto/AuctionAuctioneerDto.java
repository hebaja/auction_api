package com.hebaja.auction.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.hebaja.auction.model.Auction;

public class AuctionAuctioneerDto {

	private Long id;
	private String title;
	private String auctioneerName;
	private String auctioneerEmail;
	private Integer lotsQuantity;
	
	public AuctionAuctioneerDto(Auction auction) {
		this.id = auction.getId();
		this.title = auction.getTitle();
		this.auctioneerName = auction.getAuctioneer().getName();
		this.auctioneerEmail = auction.getAuctioneer().getEmail();
		this.lotsQuantity = auction.getLots().size();
	}
	
	public static List<AuctionAuctioneerDto> convertToList(List<Auction> auctions) {
		return auctions.stream().map(AuctionAuctioneerDto::new).collect(Collectors.toList());
	}

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

	public String getAuctioneerName() {
		return auctioneerName;
	}

	public void setAuctioneerName(String auctioneerName) {
		this.auctioneerName = auctioneerName;
	}

	public Integer getLotsQuantity() {
		return lotsQuantity;
	}

	public void setLotsQuantity(Integer lotsQuantity) {
		this.lotsQuantity = lotsQuantity;
	}

	public String getAuctioneerEmail() {
		return auctioneerEmail;
	}

	public void setAuctioneerEmail(String auctioneerEmail) {
		this.auctioneerEmail = auctioneerEmail;
	}

}
