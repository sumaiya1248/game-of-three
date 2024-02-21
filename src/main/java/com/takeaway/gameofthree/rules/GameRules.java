package com.takeaway.gameofthree.rules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Policy Class containing the rules of the game
 * 
 *
 */
@Component
public class GameRules {

	@Value("${gameofthree.player.game.over-game-at}")
	private int gameOverAtNumber;

	private final  PlayerNumberPolicy playerNumberPolicy;

	public GameRules(PlayerNumberPolicy playerNumberPolicy) {
		this.playerNumberPolicy = playerNumberPolicy;
	}

	public boolean isOver(int currentNumber) {
		return currentNumber == gameOverAtNumber;
	}

	public boolean isReady() {
		return playerNumberPolicy.isRequiredPlayerNumberAchieved();
	}

}
