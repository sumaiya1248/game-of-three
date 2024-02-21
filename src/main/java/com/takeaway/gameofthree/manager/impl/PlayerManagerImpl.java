package com.takeaway.gameofthree.manager.impl;

import com.takeaway.gameofthree.manager.PlayerManager;
import com.takeaway.gameofthree.model.Player;
import com.takeaway.gameofthree.repo.PlayerRepository;
import com.takeaway.gameofthree.rules.PlayerPolicy;
import org.springframework.stereotype.Component;

/**
 * Class manager of the player model
 *
 */
@Component
public class PlayerManagerImpl implements PlayerManager {

	private final PlayerRepository playerRepository;

	private final PlayerPolicy playerPolicy;

	public PlayerManagerImpl(PlayerRepository playerRepository, PlayerPolicy playerPolicy) {
		this.playerRepository = playerRepository;
		this.playerPolicy = playerPolicy;
	}

	public void add(Player player) {
		playerPolicy.checkThatThePlayerCanBeRegistred(player.getName());

		playerRepository.save(player);
	}

	public void clearPlayers() {
		playerRepository.deleteAll();
	}

	public boolean isPlayerWaiting() {
		return playerRepository.findAll().size() == 1;
	}

}
