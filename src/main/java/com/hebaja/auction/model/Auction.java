package com.hebaja.auction.model;

import java.util.List;

import javax.persistence.CascadeType;
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
	private boolean finished = false;
	private boolean favorite;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Lot> lots;

	@ManyToOne
	private Auctioneer auctioneer;
	
	@ManyToOne
	private Auctioneer favoritedAuctioneer;
	
	private Long favoritedAuctionId;
	
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

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	@Override
	public int compareTo(Auction otherAuction) {
		return Long.compare(this.getId(), otherAuction.getId());
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public Auctioneer getOriginalAuctioneer() {
		return favoritedAuctioneer;
	}

	public void setOriginalAuctioneer(Auctioneer originalAuctioneer) {
		this.favoritedAuctioneer = originalAuctioneer;
	}

	public Long getFavoritedAuctionId() {
		return favoritedAuctionId;
	}

	public void setFavoritedAuctionId(Long favoritedAuctionId) {
		this.favoritedAuctionId = favoritedAuctionId;
	}
	
}
