package com.soat.fiap.videocore.notification.core.domain.vo;

import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;

/**
 * Objeto de valor que representa a mensagem de uma notificação.
 */
public record Message(String value) {

	public Message {
		validate(value);
	}

	private static void validate(String value) {
		if (value == null || value.isBlank()) {
			throw new NotificationException("A mensagem da notificação não pode ser nula ou vazia");
		}
	}
}
