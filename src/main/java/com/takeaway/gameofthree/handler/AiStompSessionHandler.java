package com.takeaway.gameofthree.handler;

import com.takeaway.gameofthree.constants.GameStateEnum;
import com.takeaway.gameofthree.dto.GameParticipateRequest;
import com.takeaway.gameofthree.dto.GamePlayRoundRequest;
import com.takeaway.gameofthree.util.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.*;

import java.lang.reflect.Type;

import static com.takeaway.gameofthree.constants.GameModeEnum.AI;
import static java.util.Arrays.asList;

public class AiStompSessionHandler implements StompSessionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AiStompSessionHandler.class);

	private String aiPlayerName;

	public AiStompSessionHandler(String aiPlayerName) {
		this.aiPlayerName = aiPlayerName;
	}

	@Override
	public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
		// Subscribing Ai to Game Of Three Websocket
		session.subscribe("/topic/gameOfThree", new StompFrameHandler() {

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				GameState gameState = (GameState) payload;
				// If the game is over, Ai should be disconnected from the websocket
				if (GameStateEnum.OVER == gameState.getState()) {
					session.disconnect();
				}
				// Here it is Ai round, so it submit an input number
				else if (!aiPlayerName.equals(gameState.getLastPlayer())) {
					session.send("/app/play", new GamePlayRoundRequest(gameState.getCurrentNumber(), aiPlayerName,
							countNumberToAdd(gameState.getCurrentNumber()), AI));
				}
			}

			@Override
			public Type getPayloadType(StompHeaders headers) {
				return GameState.class;
			}
		});
		// Ai send a request to participate in the game
		session.send("/app/participate", new GameParticipateRequest(aiPlayerName, AI));
	}

	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
	}

	@Override
	public Type getPayloadType(StompHeaders headers) {
		return GameState.class;
	}

	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		LOGGER.error(exception.getMessage(), exception);
	}

	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		LOGGER.error(exception.getMessage(), exception);
	}

	private Integer countNumberToAdd(Integer currentNumber) {
		return asList(-1, 0, 1).stream().filter(number -> (currentNumber + number) % 3 == 0).findFirst().get();
	}
}
