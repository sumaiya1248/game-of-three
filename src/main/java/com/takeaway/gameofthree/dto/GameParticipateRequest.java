package com.takeaway.gameofthree.dto;

import com.takeaway.gameofthree.constants.GameModeEnum;

public class GameParticipateRequest {
	private String playerName;

	private GameModeEnum gameMode;

	public GameParticipateRequest() {
	}

	public GameParticipateRequest(String playerName, GameModeEnum gameMode) {
		this.playerName = playerName;
		this.gameMode = gameMode;
	}

	public String getPlayerName() {
		return playerName;
	}

	public GameModeEnum getGameMode() {
		return gameMode;
	}

	public void setGameMode(GameModeEnum gameMode) {
		this.gameMode = gameMode;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
