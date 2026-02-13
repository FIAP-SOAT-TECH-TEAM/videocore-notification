package com.soat.fiap.videocore.notification.unit.vo;

import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.vo.RecipientName;
import com.soat.fiap.videocore.notification.fixture.NotificationFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do Value Object {@link RecipientName}.
 */
class RecipientNameTest {

    @Test
    void shouldCreateValidRecipientName() {
        // Arrange
        var recipientName = NotificationFixture.validRecipientName();

        // Act
        var value = recipientName.value();

        // Assert
        assertEquals("João Silva", value);
    }

    @Test
    void shouldThrowExceptionWhenRecipientNameIsNull() {
        // Arrange & Act
        var ex = assertThrows(NotificationException.class, () -> new RecipientName(null));

        // Assert
        assertEquals("O nome do destinatário da notificação não pode ser nulo ou vazio", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRecipientNameIsBlank() {
        // Arrange & Act
        var ex = assertThrows(NotificationException.class, () -> new RecipientName(" "));

        // Assert
        assertEquals("O nome do destinatário da notificação não pode ser nulo ou vazio", ex.getMessage());
    }
}