package com.takeaway.gameofthree.util;

import com.takeaway.gameofthree.constants.GameModeEnum;
import com.takeaway.gameofthree.constants.GameStateEnum;

public class GameState {

	private Integer currentNumber;

	private String lastPlayer;

	private Integer lastNumberAdded;

	private GameStateEnum state;

	private GameModeEnum gameMode;

	public GameState() {
	}

	public GameState(String lastPlayer, GameStateEnum state, GameModeEnum gameMode) {
		this.lastPlayer = lastPlayer;
		this.state = state;
		this.gameMode = gameMode;
	}

	public GameState(Integer currentNumber, String lastPlayer, GameStateEnum state, GameModeEnum gameMode) {
		this.currentNumber = currentNumber;
		this.lastPlayer = lastPlayer;
		this.state = state;
		this.gameMode = gameMode;
	}

	public GameState(Integer currentNumber, String lastPlayer, Integer lastNumberAdded, GameStateEnum state,
			GameModeEnum gameMode) {
		this.currentNumber = currentNumber;
		this.lastPlayer = lastPlayer;
		this.lastNumberAdded = lastNumberAdded;
		this.state = state;
		this.gameMode = gameMode;
	}

	public Integer getCurrentNumber() {
		return currentNumber;
	}

	public String getLastPlayer() {
		return lastPlayer;
	}

	public GameStateEnum getState() {
		return state;
	}

	public Integer getLastNumberAdded() {
		return lastNumberAdded;
	}

	public GameModeEnum getGameMode() {
		return gameMode;
	}
}
