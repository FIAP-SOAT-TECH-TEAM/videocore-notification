package com.soat.fiap.videocore.notification.core.domain.vo;


import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;

/**
 * Objeto de valor que representa o assunto de uma notificação.
 */
public record Subject(String value) {

    private static final int MAX_LENGTH = 100;

    public Subject {
        validate(value);
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new NotificationException("O assunto da notificação não pode ser nulo ou vazio");
        }

        if (value.length() > MAX_LENGTH) {
            throw new NotificationException(String.format("O assunto da notificação não pode exceder: %d caracteres", MAX_LENGTH));
        }
    }
}