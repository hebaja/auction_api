package com.hebaja.auction.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hebaja.auction.model.Auction;

public class AuctionAuctioneerDto {

	private Long id;
	private String title;
	private String auctioneerName;
	private String auctioneerEmail;
	private Integer lotsQuantity;
	private String auctioneerPictureUrl;
	private String creationDate;
	private boolean publicAuction;
	
	public AuctionAuctioneerDto(Auction auction) {
		this.id = auction.getId();
		this.title = auction.getTitle();
		this.auctioneerName = auction.getAuctioneer().getName();
		this.auctioneerEmail = auction.getAuctioneer().getEmail();
		this.lotsQuantity = auction.getLots().size();
		this.auctioneerPictureUrl = auction.getAuctioneer().getPictureUrl();
		this.publicAuction = auction.isPublicAuction();
		this.creationDate = auction.getCreationDate().toString();
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

	public String getAuctioneerPictureUrl() {
		return auctioneerPictureUrl;
	}

	public void setAuctioneerPictureUrl(String auctioneerPictureUrl) {
		this.auctioneerPictureUrl = auctioneerPictureUrl;
	}
	
	public static Page<AuctionAuctioneerDto> convertToList(Page<Auction> auctions) {
		return auctions.map(AuctionAuctioneerDto::new);
	}

	@JsonProperty(value = "public")
	public boolean isPublicAuction() {
		return publicAuction;
	}

	public void setPublicAuction(boolean publicAuction) {
		this.publicAuction = publicAuction;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

}
