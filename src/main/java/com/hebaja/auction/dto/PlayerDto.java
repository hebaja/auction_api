package com.hebaja.auction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hebaja.auction.model.Lot;
import com.hebaja.auction.model.Player;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerDto {

	private Long id;
    private String playerName;
    private String walletValue;
    private LotDto activeLotDto;
    private String groupPlayer;
    private List<LotDto> acquiredLotsDto;
    private int score;
    
    public PlayerDto(Player player, Lot activeLot) {
    	this.id = player.getId();
    	this.playerName = player.getName();
    	this.walletValue = player.getWallet().getMoney().toString();
    	this.groupPlayer = player.getGroupPlayers().getName();
    	this.acquiredLotsDto = player.getAcquiredLots().stream().map(LotDto::new).collect(Collectors.toList());
    	this.activeLotDto = new LotDto(activeLot);
    }
    
    public PlayerDto(Player player) {
    	this.id = player.getId();
    	this.playerName = player.getName();
    	this.walletValue = player.getWallet().getMoney().toString();
    	this.groupPlayer = player.getGroupPlayers().getName();
    	this.acquiredLotsDto = player.getAcquiredLots().stream().map(LotDto::new).collect(Collectors.toList());
    	this.score = player.getScore();
    }
    
    public static PlayerDto convertWithActiveLot(Player player, Lot activeLot) {
    	return new PlayerDto(player, activeLot);
    }

    public static PlayerDto convert(Player player) {
    	return new PlayerDto(player);
    }
    
    public static List<PlayerDto> convertToList(List<Player> players) {
    	return players.stream().map(PlayerDto::new).collect(Collectors.toList());
    }
    
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getWalletValue() {
        return walletValue;
    }

    public void setWalletValue(String walletValue) {
        this.walletValue = walletValue;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//    public Player toPlayer() {
//        return new Player(getPlayerName(), new BigDecimal(getWalletValue()), getGroupPlayer());
//    }
    
    @JsonProperty("activeLot")
	public LotDto getActiveLotDto() {
		return activeLotDto;
	}

	public void setActiveLotDto(LotDto activeLotDto) {
		this.activeLotDto = activeLotDto;
	}

    @JsonProperty("acquiredLots")
	public List<LotDto> getAcquiredLotsDto() {
		return acquiredLotsDto;
	}

	public void setAcquiredLotsDto(List<LotDto> acquiredLotsDto) {
		this.acquiredLotsDto = acquiredLotsDto;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getGroupPlayer() {
		return groupPlayer;
	}

	public void setGroupPlayer(String groupPlayer) {
		this.groupPlayer = groupPlayer;
	}

}
