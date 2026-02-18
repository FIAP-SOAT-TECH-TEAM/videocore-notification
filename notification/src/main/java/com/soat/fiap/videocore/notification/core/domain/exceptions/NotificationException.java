package com.soat.fiap.videocore.notification.core.domain.exceptions;

/**
 * Exceção lançada quando uma regra de negócio é violada referente a notificação
 */
public class NotificationException extends RuntimeException {

	public NotificationException(String message) {
		super(message);
	}

	public NotificationException(String message, Throwable cause) {
		super(message, cause);
	}
}
