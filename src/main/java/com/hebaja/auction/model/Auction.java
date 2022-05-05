package com.hebaja.auction.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Auction implements Comparable<Auction> {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String title;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Lot> lots;

	@ManyToOne
	private Auctioneer auctioneer;
	
	@Column(name = "public")
	private boolean publicAuction;
	
	private boolean finished;
	
	private Long favoritedCounter = 0L;
	
	private boolean favorite;
	
	private LocalDateTime creationDate = LocalDateTime.now();
	
	public Auction() {}
	
	public Auction(String title) {
		this.setTitle(title);
	}

	public List<Lot> getLots() {
		return lots;
	}

	public void setLots(List<Lot> lots) {
		this.lots = lots;
	}

	public Auctioneer getAuctioneer() {
		return auctioneer;
	}

	public void setAuctioneer(Auctioneer auctioneer) {
		this.auctioneer = auctioneer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	@Override
	public int compareTo(Auction otherAuction) {
		return Long.compare(this.getId(), otherAuction.getId());
	}

	public boolean isPublicAuction() {
		return publicAuction;
	}

	public void setPublicAuction(boolean publicAuction) {
		this.publicAuction = publicAuction;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public Long getFavoritedCounter() {
		return favoritedCounter;
	}

	public void incrementFavoriteCounter() {
		favoritedCounter++;
	}
	
	public void decrementFavoriteCounter() {
		favoritedCounter--;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
}
