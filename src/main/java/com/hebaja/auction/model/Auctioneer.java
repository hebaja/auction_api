package com.hebaja.auction.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.hebaja.auction.enums.BidAnalysisResult;
import com.hebaja.auction.task.Worker;

@Entity
public class Auctioneer extends User {
	
	private static final long serialVersionUID = 1L;

	private static final String TAG = "[Auctioneer] ";

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "auctioneer", fetch = FetchType.LAZY)
    @Cascade(CascadeType.DELETE)
    private List<GroupPlayer> groupPlayers;
    
    @OneToMany(mappedBy = "auctioneer", fetch = FetchType.LAZY)
    @Cascade(CascadeType.DELETE)
    private List<Auction> auctions;
    
    @ElementCollection
    private List<Long> finishedAuctionsIds;
    
    @ElementCollection
    private List<Long> favoriteAuctionsId;
    
    @Column(unique = true)
    private String email;
    
    @Transient
    private Worker worker;
    
    @Transient
    public void sortAuctions() {
    	Collections.sort(getAuctions());
		getAuctions().forEach(auction -> {
			Collections.sort(auction.getLots());
		});
    }
    
    @Transient
    public List<Auction> sortFavoriteAuctions(List<Auction> favoriteAuctions) {
    	Collections.sort(favoriteAuctions);
		favoriteAuctions.forEach(auction -> {
			Collections.sort(auction.getLots());
		});
		return favoriteAuctions;
    }
    
    public Auctioneer() {
		super();
	}

	public Auctioneer(String name, String email) {
		super(name);
		this.email = email;
	}

	public BidAnalysisResult analiseBid(Lot lotReceived, Bid bid) {
        if(lotReceived.isActive() == true) {
        	if(bid.getPlayer().getGroupPlayers().isActive()) {
            	if(lotReceived.getBids().isEmpty()) {
            		if(bid.getValue().compareTo(lotReceived.getStartingBid()) >= 0) {
            			System.out.println(TAG + this.getClass().toString() + " --> accepting first bid of "  + bid.getPlayer().getName() + ". with value " + bid.getValue());
                        lotReceived.getBids().add(bid);
                        return BidAnalysisResult.BID_VALID;
            		} else {
            			System.out.println(TAG + this.getClass().toString() + " *** " + bid.getPlayer().getName() + ", you bid is lower than starting bid");
            			return BidAnalysisResult.BID_LOWER_THAN_STARTING;
            		}
            	} else {
            		Collections.sort(lotReceived.getBids(), (thisBid, otherBid) -> thisBid.getValue().compareTo(otherBid.getValue()));
            		Bid lastBid = lotReceived.getBids().get(lotReceived.getBids().size() - 1);
            		System.out.println(TAG + "Last Bid made ----> " + "id: " + lastBid.getId() + " player:  " + lastBid.getPlayer().getName() + " value:  " + lastBid.getValue());
                    if(bid.getValue().compareTo(lastBid.getValue()) > 0) {
                        lotReceived.getBids().add(bid);
                        System.out.println(TAG + this.getClass().toString() + " --> accepting bid of "  + bid.getPlayer().getName() + ". with value " + bid.getValue());
                        return BidAnalysisResult.BID_VALID;
                    } else {
                        System.out.println(TAG + this.getClass().toString() + " *** " + bid.getPlayer().getName() + ", your bid is lower than last bid.");
                        return BidAnalysisResult.BID_LOWER_OR_EQUAL_THAN_LAST;
                    }
            	}
        			
        	} else {
        		System.out.println(TAG + this.getClass().toString() +  " *** " + bid.getPlayer().getName() + ", your group is not active.");
        		return BidAnalysisResult.BID_GROUP_NOT_ACTIVE;
        	}
        } else {
            System.out.println(TAG + this.getClass().toString() +  " *** " + bid.getPlayer().getName() + ", this lot has not been started yet.");
            return BidAnalysisResult.BID_LOT_NOT_STARTED;
        }
    }
	
	public void activateGroup(GroupPlayer group) {
		if(group.getAuctioneer() == this) {
    		System.out.println(this.getClass().toString() +  " activating group " + group.getName() + " for " + group.getAuctioneer().getName());
			group.setActive(true);
		} else {
			System.out.println(this.getClass().toString() +  " This group does not belong to this auctioneer");
		}
	}
	
	public void deactivateGroup(GroupPlayer group) {
		if(group.getAuctioneer() == this) {
    		System.out.println(this.getClass().toString() +  " deactivating group " + group.getName() + " for " + group.getAuctioneer().getName());
			group.setActive(false);
		} else {
			System.out.println(this.getClass().toString() +  " This group does not belong to this auctioneer");
		}
	}

    public void startLot(Lot lot, BigDecimal startingBid) {
        lot.setActive(true);
        lot.setStartingBid(startingBid);
    }

    public Lot finishLot(Lot lotReceived) {
    	if(lotReceived.getBids().size() > 0) {
    		
    		Collections.sort(lotReceived.getBids(), (bid, otherBid) -> bid.getValue().compareTo(otherBid.getValue()));
    		
    		Bid lastBid = lotReceived.getBids().get(lotReceived.getBids().size() - 1);
    		Player player = lastBid.getPlayer();
            lotReceived.setPricePaid(lastBid.getValue());
            player.getAcquiredLots().add(lotReceived);
            player.payLot(lastBid.getValue());

            lotReceived.setActive(false);

            return lotReceived;
    	} else {
    		return null;
    	}
    }
    
	public List<GroupPlayer> getGroupPlayers() {
		return groupPlayers;
	}

	public void setGroupPlayers(List<GroupPlayer> groupPlayers) {
		this.groupPlayers = groupPlayers;
	}

	public List<Auction> getAuctions() {
		return auctions;
	}

	public void setAuctions(List<Auction> auctions) {
		this.auctions = auctions;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public List<Long> getFinishedAuctionsIds() {
		return finishedAuctionsIds;
	}

	public void setFinishedAuctionsIds(List<Long> finishedAuctionsIds) {
		this.finishedAuctionsIds = finishedAuctionsIds;
	}

	public List<Long> getFavoriteAuctionsId() {
		return favoriteAuctionsId;
	}

	public void setFavoriteAuctionsId(List<Long> favoriteAuctionsId) {
		this.favoriteAuctionsId = favoriteAuctionsId;
	}


}
