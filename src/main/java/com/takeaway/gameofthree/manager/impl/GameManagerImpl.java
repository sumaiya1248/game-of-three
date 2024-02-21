package com.takeaway.gameofthree.manager.impl;

import com.takeaway.gameofthree.constants.GameModeEnum;
import com.takeaway.gameofthree.constants.GameStateEnum;
import com.takeaway.gameofthree.manager.GameManager;
import com.takeaway.gameofthree.manager.PlayerManager;
import com.takeaway.gameofthree.model.Player;
import com.takeaway.gameofthree.rules.GameRules;
import com.takeaway.gameofthree.rules.PlayerNumberPolicy;
import com.takeaway.gameofthree.util.GameRound;
import com.takeaway.gameofthree.util.GameState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

/**
 * Class manager of the game
 *
 */
@Component
public class GameManagerImpl implements GameManager {

	private PlayerManager playerManager;

	private GameRules gameRules;

	private PlayerNumberPolicy playerNumberPolicy;

	@Value("${gameofthree.player.game.max-number}")
	private int maxNumber;

	@Value("${gameofthree.player.game.min-number}")
	private int minNumber;

	public GameManagerImpl(PlayerManager playerManager, GameRules gameRules, PlayerNumberPolicy playerNumberPolicy) {
		this.playerManager = playerManager;
		this.gameRules = gameRules;
		this.playerNumberPolicy = playerNumberPolicy;
	}
	@Override
	public GameState playRound(GameRound gameRound) {
		int newCurrentNumber = countCurrentNumber(gameRound);
		GameStateEnum state = getGameState(newCurrentNumber);
		if (state == GameStateEnum.OVER) {
			playerManager.reinitPlayers();
		}
		return new GameState(newCurrentNumber, gameRound.getPlayer(), gameRound.getInputNumber(), state,
				gameRound.getGameMode());
	}
	@Override
	public GameState addNewPlayer(String playerName, GameModeEnum gameMode) {
		playerManager.add(new Player().builder().name(playerName).build());
		if (playerNumberPolicy.isRequiredPlayerNumberAchieved()) {
			return new GameState(getARandomNumber(), playerName, GameStateEnum.IN_PROGRESS, gameMode);
		}
		return new GameState(playerName, GameStateEnum.WAITING_FOR_PLAYER, gameMode);
	}

	private GameStateEnum getGameState(int currentNumber) {
		if (gameRules.isOver(currentNumber)) {
			return GameStateEnum.OVER;
		}
		return GameStateEnum.IN_PROGRESS;
	}

	private int countCurrentNumber(GameRound gameRound) {
		return (gameRound.getCurrentNumber() + gameRound.getInputNumber()) / 3;
	}

	private int getARandomNumber() {
		return new Random().ints(minNumber, maxNumber).findFirst().getAsInt();
	}

}
