package com.soat.fiap.videocore.notification.unit.vo;

import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.vo.Recipient;
import com.soat.fiap.videocore.notification.fixture.NotificationFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do Value Object {@link Recipient}.
 */
class RecipientTest {

    @Test
    void shouldCreateValidRecipient() {
        // Arrange
        var recipient = NotificationFixture.validRecipient();

        // Act
        var value = recipient.value();

        // Assert
        assertEquals("joao@email.com", value);
    }

    @Test
    void shouldThrowExceptionWhenRecipientIsNull() {
        // Arrange & Act
        var ex = assertThrows(NotificationException.class, () -> new Recipient(null));

        // Assert
        assertEquals("O destinatário da notificação não pode ser nulo ou vazio", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRecipientIsBlank() {
        // Arrange & Act
        var ex = assertThrows(NotificationException.class, () -> new Recipient(" "));

        // Assert
        assertEquals("O destinatário da notificação não pode ser nulo ou vazio", ex.getMessage());
    }
}