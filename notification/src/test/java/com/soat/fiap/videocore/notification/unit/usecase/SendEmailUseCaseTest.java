package com.soat.fiap.videocore.notification.unit.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.soat.fiap.videocore.notification.core.application.usecase.SendEmailUseCase;
import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.interfaceadapters.gateway.EmailGateway;

/**
 * Testes unitários do caso de uso {@link SendEmailUseCase}.
 */
class SendEmailUseCaseTest {

	private final EmailGateway emailGateway = mock(EmailGateway.class);
	private final SendEmailUseCase useCase = new SendEmailUseCase(emailGateway);

	@Test
	void shouldSendEmailWhenNotificationIsValid() {
		// Arrange
		var notification = mock(Notification.class);
		when(notification.getSubject()).thenReturn("subject");
		when(notification.getRecipient()).thenReturn("to@email.com");
		when(notification.getMessage()).thenReturn("message");

		// Act
		useCase.sendEmail(notification);

		// Assert
		verify(emailGateway).sendEmail("subject", "to@email.com", "message");
	}

	@Test
	void shouldThrowExceptionWhenNotificationIsNull() {
		// Arrange & Act
		var ex = assertThrows(NotificationException.class, () -> useCase.sendEmail(null));

		// Assert
		assertEquals("A notificação não pode ser nula para o disparo de emails", ex.getMessage());
	}
}
