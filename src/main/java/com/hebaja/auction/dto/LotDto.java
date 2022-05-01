package com.hebaja.auction.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hebaja.auction.model.Auctioneer;
import com.hebaja.auction.model.Bid;
import com.hebaja.auction.model.Lot;

public class LotDto {
	
	private final String TAG = "[LotDto] ";

	private Long id;
    private String title;
    private String description;
    private BigDecimal startingBid;
    private BigDecimal pricePaid;
    private boolean active;
    private boolean correct;
    private List<BidDto> bidsDto;
    private boolean existActiveGroup;
    
    public LotDto(Lot lot) {
    	this.id = lot.getId();
    	this.setTitle(lot.getTitle());
    	this.setDescription(lot.getDescription());
    	this.startingBid = lot.getStartingBid();
    	this.active = lot.isActive();
    	Collections.sort(lot.getBids(), (bid, otherBid) -> bid.getValue().compareTo(otherBid.getValue()));
    	this.setBidsDto(lot.getBids().stream().map(BidDto::new).collect(Collectors.toList()));
		this.correct = lot.isCorrect();
    	if(lot.getPricePaid() != null) {
    		this.pricePaid = lot.getPricePaid();
    	}
    	existActiveGroup = lot.getAuction().getAuctioneer().getGroupPlayers().stream().anyMatch(group -> group.isActive());
    }
    
    public LotDto(Lot lot, Auctioneer auctioneer) {
    	this.id = lot.getId();
    	this.setTitle(lot.getTitle());
    	this.setDescription(lot.getDescription());
    	this.startingBid = lot.getStartingBid();
    	this.active = lot.isActive();
    	Collections.sort(lot.getBids(), (bid, otherBid) -> bid.getValue().compareTo(otherBid.getValue()));
    	this.setBidsDto(lot.getBids().stream().map(BidDto::new).collect(Collectors.toList()));
		this.correct = lot.isCorrect();
    	if(lot.getPricePaid() != null) {
    		this.pricePaid = lot.getPricePaid();
    	}
    	existActiveGroup = auctioneer.getGroupPlayers().stream().anyMatch(group -> group.isActive());
	}

	public static LotDto convert(Lot lot) {
    	return new LotDto(lot);
    }

    public Lot toLot() {
    	Lot lot = new Lot();
    	lot.setTitle(getTitle());
    	lot.setDescription(getDescription());
        return lot;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public BigDecimal getStartingBid() {
		return startingBid;
	}

	public void setStartingBid(BigDecimal startingBid) {
		this.startingBid = startingBid;
	}

	@JsonProperty("bids")
	public List<BidDto> getBidsDto() {
		return bidsDto;
	}

	public void setBidsDto(List<BidDto> bidsDto) {
		this.bidsDto = bidsDto;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public BigDecimal getPricePaid() {
		return pricePaid;
	}
	
	public void setPricePaid(BigDecimal pricePaid) {
		this.pricePaid = pricePaid;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public boolean isExistActiveGroup() {
		return existActiveGroup;
	}

	public void setExistActiveGroup(boolean existActiveGroup) {
		this.existActiveGroup = existActiveGroup;
	}

}
