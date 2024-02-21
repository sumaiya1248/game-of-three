package com.takeaway.gameofthree.rules;

import com.takeaway.gameofthree.exception.PlayerCannotBeRegistredException;
import com.takeaway.gameofthree.exception.PlayerCannotBeRegistredReason;
import com.takeaway.gameofthree.model.Player;
import com.takeaway.gameofthree.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Policy class containing player number rules
 *
 */
@Component
public class PlayerNumberPolicy {

	private PlayerRepository playerRepository;

	@Value("${gameofthree.game.player.max-players}")
	private int requiredPlayerNumberToStart;

	public PlayerNumberPolicy(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;

	}

	public void checkThatOtherPlayerIsRequired() {
		if (isRequiredPlayerNumberAchieved()) {
			throw new PlayerCannotBeRegistredException(PlayerCannotBeRegistredReason.REQUIRED_PLAYER_NUMBER_ACHIEVED);
		}
	}

	public boolean isRequiredPlayerNumberAchieved() {
		List<Player> playerAlreadyRegistred = playerRepository.findAll();
		return !isEmpty(playerAlreadyRegistred) && playerAlreadyRegistred.size() >= requiredPlayerNumberToStart;
	}

}
