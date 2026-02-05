package com.soat.fiap.videocore.notification.core.domain.vo;


import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;

/**
 * Objeto de valor que representa a mensagem de uma notificação.
 */
public record Message(String value) {

    private static final int MAX_LENGTH = 255;

    public Message {
        validate(value);
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new NotificationException("A mensagem da notificação não pode ser nula ou vazia");
        }

        if (value.length() > MAX_LENGTH) {
            throw new NotificationException(String.format("A mensagem da notificação não pode exceder: %d caracteres", MAX_LENGTH));
        }
    }
}