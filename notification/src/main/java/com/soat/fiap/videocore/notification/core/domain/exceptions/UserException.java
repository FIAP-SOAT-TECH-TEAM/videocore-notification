package com.soat.fiap.videocore.notification.core.domain.exceptions;

/**
 * Exceção lançada quando um erro ocorre referente a um usuário
 */
public class UserException extends RuntimeException {

	public UserException(String message) {
		super(message);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}
}