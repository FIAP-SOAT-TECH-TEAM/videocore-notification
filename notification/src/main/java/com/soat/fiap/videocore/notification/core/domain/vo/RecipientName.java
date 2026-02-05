package com.soat.fiap.videocore.notification.core.domain.vo;

import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;

/**
 * Objeto de valor que representa o nome do destinatário de uma notificação.
 */
public record RecipientName(String value) {
    public RecipientName {
        validate(value);
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new NotificationException("O nome do destinatário da notificação não pode ser nulo ou vazio");
        }
    }
}