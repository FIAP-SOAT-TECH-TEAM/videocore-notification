package com.soat.fiap.videocore.notification.unit.controller;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.videocore.notification.core.application.usecase.CreateEmailNotificationErrorProcessUseCase;
import com.soat.fiap.videocore.notification.core.application.usecase.GetUserByIdUseCase;
import com.soat.fiap.videocore.notification.core.application.usecase.SendEmailUseCase;
import com.soat.fiap.videocore.notification.core.interfaceadapters.controller.ProcessVideoErrorController;
import com.soat.fiap.videocore.notification.core.interfaceadapters.mapper.EventMapper;
import com.soat.fiap.videocore.notification.fixture.NotificationFixture;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoErrorPayload;

/**
 * Testes unitários do controller {@link ProcessVideoErrorController}. Garante
 * cobertura total do fluxo de orquestração de erro no processamento de vídeo.
 */
@ExtendWith(MockitoExtension.class)
class ProcessVideoErrorControllerTest {

	@Mock
	private GetUserByIdUseCase getUserByIdUseCase;
	@Mock
	private CreateEmailNotificationErrorProcessUseCase createEmailNotificationErrorProcessUseCase;
	@Mock
	private SendEmailUseCase sendEmailUseCase;
	@Mock
	private EventMapper eventMapper;

	@InjectMocks
	private ProcessVideoErrorController controller;

	@Test
	void shouldProcessVideoErrorAndSendEmail() {
		// Arrange
		var payload = mock(ProcessVideoErrorPayload.class);
		var user = NotificationFixture.user();
		var input = NotificationFixture.notificationErrorInput();
		var notification = NotificationFixture.notification();

		when(payload.userId()).thenReturn("user-id");
		when(getUserByIdUseCase.getUserById("user-id")).thenReturn(user);
		when(eventMapper.toInput(payload)).thenReturn(input);
		when(createEmailNotificationErrorProcessUseCase.createEmailNotificationErrorProcess(user, input))
				.thenReturn(notification);

		// Act
		controller.processVideoError(payload);

		// Assert
		verify(sendEmailUseCase).sendEmail(notification);
	}
}
