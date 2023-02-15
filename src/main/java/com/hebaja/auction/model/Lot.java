package com.hebaja.auction.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "lot")
public class Lot implements Comparable<Lot>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String title;
    private String description;
    private boolean active = false;
    private BigDecimal startingBid;
    private BigDecimal pricePaid;
    private boolean correct;

    public Lot() {}

    public Lot(String title, String description) {
		this.title = title;
		this.description = description;
	}
    
    public Lot(String title, String description, boolean correct, Auction auction) {
		this.title = title;
		this.description = description;
		this.correct = correct;
		this.auction = auction;
	}


    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "lot")
    private List<Bid> bids = new ArrayList<>();
    
    @ManyToOne
    private Auction auction;
    
    @Transient
    private boolean readOnly = true;

    public long getId() {
        return id;
    }

	public BigDecimal getStartingBid() {
		return startingBid;
	}

	public void setStartingBid(BigDecimal startingBid) {
		this.startingBid = startingBid;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public List<Bid> getBids() {
		return bids;
	}

	public BigDecimal getPricePaid() {
		return pricePaid;
	}

	public void setPricePaid(BigDecimal pricePaid) {
		this.pricePaid = pricePaid;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

	public void setBids(List<Bid> bids) {
		this.bids = bids;
	}

	@Override
	public int compareTo(Lot otherLot) {
		return Long.compare(this.getId(), otherLot.getId());
	}
}
