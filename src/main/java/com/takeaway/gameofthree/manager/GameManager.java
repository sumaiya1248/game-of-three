package com.takeaway.gameofthree.manager;

import com.takeaway.gameofthree.constants.GameModeEnum;
import com.takeaway.gameofthree.util.GameRound;
import com.takeaway.gameofthree.util.GameState;
import org.springframework.stereotype.Component;

@Component
public interface GameManager {

	GameState playRound(GameRound gameRound);

	GameState addNewPlayer(String player, GameModeEnum gameMode);

}
