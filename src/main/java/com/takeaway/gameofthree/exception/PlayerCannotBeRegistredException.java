package com.takeaway.gameofthree.exception;

import static java.lang.String.format;

public class PlayerCannotBeRegistredException extends RuntimeException {

	private static final long serialVersionUID = -6945847183138381340L;

	private static final String REGISTRATION_ERROR_MESSAGE = "A new player cannot be registred beacause %s.";

	public PlayerCannotBeRegistredException(PlayerCannotBeRegistredReason reason) {
		super(format(REGISTRATION_ERROR_MESSAGE, reason.getMessage()));
	}

}
