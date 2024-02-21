package com.takeaway.gameofthree.rules;

import com.takeaway.gameofthree.exception.PlayerCannotBeRegistredException;
import com.takeaway.gameofthree.exception.PlayerCannotBeRegistredReason;
import com.takeaway.gameofthree.model.Player;
import com.takeaway.gameofthree.repo.PlayerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * Policy class containing player name rules
 *
 */
@Component
public class PlayerNamePolicy {

	private PlayerRepository playerRepository;

	public PlayerNamePolicy(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	public void checkThatPlayerNameIsNotUsed(String playerName) {
		if (playerWithSameNameExists(playerName)) {
			throw new PlayerCannotBeRegistredException(PlayerCannotBeRegistredReason.REQUIRED_PLAYER_NAME_USED);
		}
	}

	private boolean playerWithSameNameExists(String playerName) {
		List<Player> playerAlreadyRegistred = playerRepository.findAll();
		return !isEmpty(playerAlreadyRegistred)
				&& playerAlreadyRegistred.stream().filter(playerWithSameName(playerName)).findFirst().isPresent();
	}

	private Predicate<Player> playerWithSameName(String playerName) {
		return player -> player.getName().equals(playerName);
	}

}
