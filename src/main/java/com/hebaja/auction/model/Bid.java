package com.hebaja.auction.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    public Bid() {}

    private BigDecimal value;
    
    @ManyToOne
    private Lot lot;

    @OneToOne
    private Player player;
    
    public Bid(BigDecimal value, Player player, Lot lot) {
        this.value = value;
        this.player = player;
        this.lot = lot;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Player getPlayer() {
        return player;
    }

	public long getId() {
		return id;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

}
