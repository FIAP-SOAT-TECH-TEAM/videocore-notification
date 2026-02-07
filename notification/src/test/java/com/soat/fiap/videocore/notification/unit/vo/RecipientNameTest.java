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
        // arrange
        var recipientName = NotificationFixture.validRecipientName();

        // act
        var value = recipientName.value();

        // assert
        assertEquals("João Silva", value);
    }

    @Test
    void shouldThrowExceptionWhenRecipientNameIsNull() {
        // arrange / act
        var ex = assertThrows(NotificationException.class, () -> new RecipientName(null));

        // assert
        assertEquals("O nome do destinatário da notificação não pode ser nulo ou vazio", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRecipientNameIsBlank() {
        // arrange / act
        var ex = assertThrows(NotificationException.class, () -> new RecipientName(" "));

        // assert
        assertEquals("O nome do destinatário da notificação não pode ser nulo ou vazio", ex.getMessage());
    }
}