package com.hebaja.auction.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hebaja.auction.model.GroupPlayer;

public class GroupPlayerDto {

	private Long id;
	private Long auctioneerId;
	private String name;
	private boolean active;
	private List<PlayerDto> playersDto;
	
	public GroupPlayerDto(GroupPlayer groupPlayers) {
		this.id = groupPlayers.getId();
		this.auctioneerId = groupPlayers.getAuctioneer().getId();
		this.name = groupPlayers.getName();
		this.active = groupPlayers.isActive();
		this.playersDto = groupPlayers.getPlayers().stream().map(PlayerDto::new).collect(Collectors.toList());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@JsonProperty("players")
	public List<PlayerDto> getPlayersDto() {
		return playersDto;
	}

	public void setPlayersDto(List<PlayerDto> playersDto) {
		this.playersDto = playersDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public static List<GroupPlayerDto> convertToList(List<GroupPlayer> groupPlayersList) {
		return groupPlayersList.stream().map(GroupPlayerDto::new).collect(Collectors.toList());
	}
	
	public static GroupPlayerDto convert(GroupPlayer groupPlayer) {
		return new GroupPlayerDto(groupPlayer);
	}

	public Long getAuctioneerId() {
		return auctioneerId;
	}

	public void setAuctioneerId(Long auctioneerId) {
		this.auctioneerId = auctioneerId;
	}

}
