package com.soat.fiap.videocore.notification.unit.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.vo.Message;
import com.soat.fiap.videocore.notification.fixture.NotificationFixture;

/**
 * Testes unitários do Value Object {@link Message}.
 */
class MessageTest {

	@Test
	void shouldCreateValidMessage() {
		// Arrange
		var message = NotificationFixture.validMessage();

		// Act
		var value = message.value();

		// Assert
		assertEquals("Mensagem válida", value);
	}

	@Test
	void shouldThrowExceptionWhenMessageIsNull() {
		// Arrange & Act
		var ex = assertThrows(NotificationException.class, () -> new Message(null));

		// Assert
		assertEquals("A mensagem da notificação não pode ser nula ou vazia", ex.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenMessageIsBlank() {
		// Arrange & Act
		var ex = assertThrows(NotificationException.class, () -> new Message(" "));

		// Assert
		assertEquals("A mensagem da notificação não pode ser nula ou vazia", ex.getMessage());
	}
}
