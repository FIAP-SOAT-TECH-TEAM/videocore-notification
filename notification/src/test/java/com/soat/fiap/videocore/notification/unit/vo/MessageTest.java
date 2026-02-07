package com.soat.fiap.videocore.notification.unit.vo;

import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.vo.Message;
import com.soat.fiap.videocore.notification.fixture.NotificationFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do Value Object {@link Message}.
 */
class MessageTest {

    @Test
    void shouldCreateValidMessage() {
        // arrange
        var message = NotificationFixture.validMessage();

        // act
        var value = message.value();

        // assert
        assertEquals("Mensagem válida", value);
    }

    @Test
    void shouldThrowExceptionWhenMessageIsNull() {
        // arrange / act
        var ex = assertThrows(NotificationException.class, () -> new Message(null));

        // assert
        assertEquals("A mensagem da notificação não pode ser nula ou vazia", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageIsBlank() {
        // arrange / act
        var ex = assertThrows(NotificationException.class, () -> new Message(" "));

        // assert
        assertEquals("A mensagem da notificação não pode ser nula ou vazia", ex.getMessage());
    }
}