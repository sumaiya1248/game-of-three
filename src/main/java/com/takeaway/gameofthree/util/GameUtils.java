package com.takeaway.gameofthree.util;

import com.takeaway.gameofthree.constants.GameModeEnum;
import com.takeaway.gameofthree.dto.GameParticipateRequest;
import com.takeaway.gameofthree.handler.AiStompSessionHandler;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class GameUtils {

	public static boolean shouldAddAiPlayer(GameParticipateRequest gameParticipateRequest, String aiPlayerName) {
		return GameModeEnum.AI == gameParticipateRequest.getGameMode()
				&& !aiPlayerName.equals(gameParticipateRequest.getPlayerName());
	}

	public static void addAiParticipant(String aiPlayerName) {
		WebSocketClient client = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(client);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		stompClient.connectAsync("ws://localhost:8080/game-of-three-websocket", new AiStompSessionHandler(aiPlayerName));
	}

}
