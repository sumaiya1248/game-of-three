package com.takeaway.gameofthree.controller;

import com.takeaway.gameofthree.dto.GameParticipateRequest;
import com.takeaway.gameofthree.dto.GamePlayRoundRequest;
import com.takeaway.gameofthree.manager.GameManager;
import com.takeaway.gameofthree.manager.PlayerManager;
import com.takeaway.gameofthree.util.GameState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.takeaway.gameofthree.util.GameUtils.addAiParticipant;
import static com.takeaway.gameofthree.util.GameUtils.shouldAddAiPlayer;


@Controller
@Slf4j
public class GameController {

	private final GameManager gameManager;

	private final PlayerManager playerManager;

	public GameController(GameManager gameManager, PlayerManager playerManager) {
		this.gameManager = gameManager;
		this.playerManager = playerManager;
	}

	@Value("${gameofthree.game.ai-name}")
	private String aiPlayerName;
	@MessageMapping("/participate")
	@SendTo("/topic/gameOfThree")
	public GameState participate(GameParticipateRequest gameParticipateRequest) throws InterruptedException {
		log.info("in participate method");
		GameState gameState = gameManager.addNewPlayer(gameParticipateRequest.getPlayerName(),
				gameParticipateRequest.getGameMode());
		if (shouldAddAiPlayer(gameParticipateRequest, aiPlayerName)) {
			addAiParticipant(aiPlayerName);
		}
		return gameState;
	}

	@MessageMapping("/play")
	@SendTo("/topic/gameOfThree")
	public GameState play(GamePlayRoundRequest gamePlayRoundRequest) throws InterruptedException {
		return gameManager.playRound(gamePlayRoundRequest.getGameRound());
	}

	@GetMapping("/isPlayerWaiting")
	@ResponseBody
	public boolean isPlayerWaiting() {
		return playerManager.isPlayerWaiting();
	}

}
