package com.takeaway.gameofthree.manager;

import com.takeaway.gameofthree.model.Player;
import org.springframework.stereotype.Component;

@Component
public interface PlayerManager {

	void add(Player player);

	void reinitPlayers();

	boolean isPlayerWaiting();
}
