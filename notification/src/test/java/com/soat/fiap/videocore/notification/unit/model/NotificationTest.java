package com.soat.fiap.videocore.notification.unit.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.fixture.NotificationFixture;

/**
 * Testes unitários da entidade {@link Notification}.
 */
class NotificationTest {

	@Test
	void shouldCreateValidNotification() {
		// Arrange
		var notification = NotificationFixture.validNotification();

		// Act & Assert
		assertEquals("João Silva", notification.getRecipientName());
		assertEquals("Assunto válido", notification.getSubject());
		assertEquals("joao@email.com", notification.getRecipient());
		assertEquals("Mensagem válida", notification.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenRecipientNameIsNull() {
		// Arrange
		var subject = NotificationFixture.validSubject();
		var recipient = NotificationFixture.validRecipient();
		var message = NotificationFixture.validMessage();

		// Act
		var ex = assertThrows(NullPointerException.class, () -> new Notification(null, subject, recipient, message));

		// Assert
		assertEquals("recipientName não pode ser nulo", ex.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenSubjectIsNull() {
		// Arrange
		var recipientName = NotificationFixture.validRecipientName();
		var recipient = NotificationFixture.validRecipient();
		var message = NotificationFixture.validMessage();

		// Act
		var ex = assertThrows(NullPointerException.class,
				() -> new Notification(recipientName, null, recipient, message));

		// Assert
		assertEquals("subject não pode ser nulo", ex.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenRecipientIsNull() {
		// Arrange
		var recipientName = NotificationFixture.validRecipientName();
		var subject = NotificationFixture.validSubject();
		var message = NotificationFixture.validMessage();

		// Act
		var ex = assertThrows(NullPointerException.class,
				() -> new Notification(recipientName, subject, null, message));

		// Assert
		assertEquals("recipient não pode ser nulo", ex.getMessage());
	}

	@Test
	void shouldThrowExceptionWhenMessageIsNull() {
		// Arrange
		var recipientName = NotificationFixture.validRecipientName();
		var subject = NotificationFixture.validSubject();
		var recipient = NotificationFixture.validRecipient();

		// Act
		var ex = assertThrows(NullPointerException.class,
				() -> new Notification(recipientName, subject, recipient, null));

		// Assert
		assertEquals("message não pode ser nulo", ex.getMessage());
	}
}
