package com.takeaway.gameofthree.exception;

public enum PlayerCannotBeRegistredReason {
	REQUIRED_PLAYER_NUMBER_ACHIEVED("Required player number is already achieved"),
	REQUIRED_PLAYER_NAME_USED("Player name is already used");

	private String message;

	private PlayerCannotBeRegistredReason(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
