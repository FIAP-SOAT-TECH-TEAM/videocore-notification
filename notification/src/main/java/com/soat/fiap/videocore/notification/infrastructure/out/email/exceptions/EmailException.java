package com.soat.fiap.videocore.notification.infrastructure.out.email.exceptions;

/**
 * Exceção lançada quando ocorre algum erro relacionado ao envio de e-mails
 */
public class EmailException extends RuntimeException {

	public EmailException(String message) {
		super(message);
	}

	public EmailException(String message, Throwable cause) {
		super(message, cause);
	}
}
