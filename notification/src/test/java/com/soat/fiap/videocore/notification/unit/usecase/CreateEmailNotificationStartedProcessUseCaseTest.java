package com.soat.fiap.videocore.notification.unit.usecase;

import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoStatusUpdateInput;
import com.soat.fiap.videocore.notification.core.application.usecase.CreateEmailNotificationStartedProcessUseCase;
import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do caso de uso {@link CreateEmailNotificationStartedProcessUseCase}.
 */
class CreateEmailNotificationStartedProcessUseCaseTest {

    private final CreateEmailNotificationStartedProcessUseCase useCase =
            new CreateEmailNotificationStartedProcessUseCase();

    @Test
    void shouldCreateNotificationWhenInputIsValid() {
        // arrange
        var user = new UserDTO("1", "Ana", "ana@email.com");
        var input = new ProcessVideoStatusUpdateInput(
                "video.mp4", UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1, 10.0, Instant.now(), false
        );

        // act
        Notification notification =
                useCase.createEmailNotificationStartedProcess(user, input);

        // assert
        assertNotNull(notification);
        assertEquals("Ana", notification.getRecipientName());
        assertTrue(notification.getSubject().contains("começou"));
    }

    @Test
    void shouldThrowExceptionWhenUserIsNull() {
        // arrange
        var input = new ProcessVideoStatusUpdateInput(
                "video.mp4", null, UUID.randomUUID().toString(), 1, 1, 10.0, Instant.now(), false
        );

        // act
        var ex = assertThrows(NotificationException.class,
                () -> useCase.createEmailNotificationStartedProcess(null, input));

        // assert
        assertEquals(
                "As informações do usuário ou do processamento do vídeo não podem ser nulas para criação da notificação",
                ex.getMessage()
        );
    }
}