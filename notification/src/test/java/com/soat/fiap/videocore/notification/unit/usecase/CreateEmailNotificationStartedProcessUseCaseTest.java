package com.soat.fiap.videocore.notification.unit.usecase;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoStatusUpdateInput;
import com.soat.fiap.videocore.notification.core.application.usecase.CreateEmailNotificationStartedProcessUseCase;
import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;

/**
 * Testes unitários do caso de uso
 * {@link CreateEmailNotificationStartedProcessUseCase}.
 */
class CreateEmailNotificationStartedProcessUseCaseTest {

	private final CreateEmailNotificationStartedProcessUseCase useCase = new CreateEmailNotificationStartedProcessUseCase();

	@Test
	void shouldCreateNotificationWhenInputIsValid() {
		// Arrange
		var user = new UserDTO("1", "Ana", "ana@email.com");
		var input = new ProcessVideoStatusUpdateInput("video.mp4", UUID.randomUUID().toString(),
				UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1, 10.0, Instant.now(), false);

		// Act
		Notification notification = useCase.createEmailNotificationStartedProcess(user, input);

		// Assert
		assertNotNull(notification);
		assertEquals("Ana", notification.getRecipientName());
		assertTrue(notification.getSubject().contains("começou"));
	}

	@Test
	void shouldThrowExceptionWhenUserIsNull() {
		// Arrange
		var input = new ProcessVideoStatusUpdateInput("video.mp4", null, UUID.randomUUID().toString(),
				UUID.randomUUID().toString(), 1, 1, 10.0, Instant.now(), false);

		// Act
		var ex = assertThrows(NotificationException.class,
				() -> useCase.createEmailNotificationStartedProcess(null, input));

		// Assert
		assertEquals(
				"As informações do usuário ou do processamento do vídeo não podem ser nulas para criação da notificação",
				ex.getMessage());
	}
}
