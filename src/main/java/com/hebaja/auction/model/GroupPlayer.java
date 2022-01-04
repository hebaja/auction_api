package com.hebaja.auction.model;

import java.util.ArrayList;
import java.util.Comparator;
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
public class GroupPlayer {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	private String name;
	
	@ManyToOne
	private Auctioneer auctioneer;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Player> players;
	
	private boolean active = false;
	
	public GroupPlayer() {}
	
	public GroupPlayer(String name) {
		this.name = name;
		this.players = new ArrayList<Player>();
	}
	
	public GroupPlayer(String name, List<Player> players, Auctioneer auctioneer) {
		this.name = name;
		this.players = players;
		this.auctioneer = auctioneer;
	}
	
	public void add(Player player) {
		player.setGroupPlayers(this);
		getPlayers().add(player);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public Auctioneer getAuctioneer() {
		return auctioneer;
	}

	public void setAuctioneer(Auctioneer auctioneer) {
		this.auctioneer = auctioneer;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public long getId() {
		return id;
	}
	
}
