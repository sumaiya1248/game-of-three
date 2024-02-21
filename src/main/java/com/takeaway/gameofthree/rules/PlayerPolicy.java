package com.takeaway.gameofthree.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Policy class containing player rules
 *
 */
@Component
public class PlayerPolicy {

	@Autowired
	private PlayerNumberPolicy playerNumberPolicy;

	@Autowired
	private PlayerNamePolicy playerNamePolicy;

	public PlayerPolicy(PlayerNumberPolicy playerNumberPolicy, PlayerNamePolicy playerNamePolicy) {
		this.playerNumberPolicy = playerNumberPolicy;
		this.playerNamePolicy = playerNamePolicy;
	}

	public void checkThatThePlayerCanBeRegistred(String playerName) {
		playerNumberPolicy.checkThatOtherPlayerIsRequired();

		playerNamePolicy.checkThatPlayerNameIsNotUsed(playerName);
	}

}
