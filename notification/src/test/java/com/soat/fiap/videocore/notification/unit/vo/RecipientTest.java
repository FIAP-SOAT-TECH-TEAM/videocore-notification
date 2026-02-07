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
        // arrange
        var recipient = NotificationFixture.validRecipient();

        // act
        var value = recipient.value();

        // assert
        assertEquals("joao@email.com", value);
    }

    @Test
    void shouldThrowExceptionWhenRecipientIsNull() {
        // arrange / act
        var ex = assertThrows(NotificationException.class, () -> new Recipient(null));

        // assert
        assertEquals("O destinatário da notificação não pode ser nulo ou vazio", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRecipientIsBlank() {
        // arrange / act
        var ex = assertThrows(NotificationException.class, () -> new Recipient(" "));

        // assert
        assertEquals("O destinatário da notificação não pode ser nulo ou vazio", ex.getMessage());
    }
}