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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Player extends User{

    private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Lot> acquiredLots = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    private GroupPlayer groupPlayer;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet = new Wallet();
    
    @Transient
    private BigDecimal initialValueInWallet;
    
    @Transient
    private boolean readOnly = true;
    
    @Transient
    private Bid lastBid;
    
    @Transient
    private int score = 0;
    
    public Player() {}
    
    public Player(String name, BigDecimal initialValueInWallet, GroupPlayer groupPlayer) {
        super(name);
        setInitialValueInWallet(initialValueInWallet);
        setGroupPlayers(groupPlayer);
    }

	public Bid makeBid(BigDecimal value, Lot lot) {
        if(this.getWallet().getMoney().compareTo(value) >= 0) {
	    	Bid bid = new Bid(value, this, lot);
	    	System.out.println(this.getClass().toString() + " ----> bid made: " + value + " - player -> " + this.getName() + " lot -> " + lot.getTitle());
	        return bid;
        }
        return null;
    }
	
	public void payLot(BigDecimal pricePaid) {
        BigDecimal subtractedValue = this.getWallet().getMoney().subtract(pricePaid);
        this.getWallet().setMoney(subtractedValue);
    }
	
	public void incrementScore() {
		this.score = this.score + 1;
	}
    
	public BigDecimal getInitialValueInWallet() {
		return this.wallet.getMoney();
	}

	public void setInitialValueInWallet(BigDecimal initialValueInWallet) {
		this.wallet.setMoney(initialValueInWallet);
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public GroupPlayer getGroupPlayers() {
		return groupPlayer;
	}

	public void setGroupPlayers(GroupPlayer groupPlayers) {
		this.groupPlayer = groupPlayers;
	}

	public long getId() {
		return id;
	}

	public List<Lot> getAcquiredLots() {
		return acquiredLots;
	}
	
	public void setAcquiredLots(List<Lot> acquiredLots) {
		this.acquiredLots = acquiredLots;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Wallet getWallet() {
		return wallet;
	}

}
