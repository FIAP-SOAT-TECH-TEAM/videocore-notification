package com.soat.fiap.videocore.notification.core.domain.vo;

import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;

/**
 * Objeto de valor que representa o destinatário de uma notificação.
 */
public record Recipient(String value) {
    public Recipient {
        validate(value);
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new NotificationException("O destinatário da notificação não pode ser nulo ou vazio");
        }
    }
}