package com.soat.fiap.videocore.notification.core.domain.exceptions;

/**
 * Exceção lançada quando um usuário não é encontrado
 */
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message, Object... args) {
		super(String.format(message, args));
	}
}
