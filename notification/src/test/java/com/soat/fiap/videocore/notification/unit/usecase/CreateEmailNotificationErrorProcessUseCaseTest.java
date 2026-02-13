package com.soat.fiap.videocore.notification.unit.usecase;

import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoErrorInput;
import com.soat.fiap.videocore.notification.core.application.usecase.CreateEmailNotificationErrorProcessUseCase;
import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.exceptions.UserException;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do caso de uso {@link CreateEmailNotificationErrorProcessUseCase}.
 */
class CreateEmailNotificationErrorProcessUseCaseTest {

    private final CreateEmailNotificationErrorProcessUseCase useCase =
            new CreateEmailNotificationErrorProcessUseCase();

    @Test
    void shouldCreateNotificationWhenInputIsValid() {
        // Arrange
        var user = new UserDTO("1", "João", "joao@email.com");
        var input = new ProcessVideoErrorInput(
                "video.mp4", UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 0, 0.0, Instant.now()
        );

        // Act
        Notification notification = useCase.createEmailNotificationErrorProcess(user, input);

        // Assert
        assertNotNull(notification);
        assertEquals("João", notification.getRecipientName());
        assertEquals("joao@email.com", notification.getRecipient());
        assertTrue(notification.getSubject().contains("processamento"));
    }

    @Test
    void shouldThrowExceptionWhenUserIsNull() {
        // Arrange
        var input = new ProcessVideoErrorInput(
                "video.mp4", null, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 20.0, Instant.now()
        );

        // Act
        var ex = assertThrows(NotificationException.class,
                () -> useCase.createEmailNotificationErrorProcess(null, input));

        // Assert
        assertEquals(
                "As informações do usuário ou do processamento do vídeo não podem ser nulas para criação da notificação",
                ex.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenEmailIsInvalid() {
        // Arrange
        var user = new UserDTO("1", "João", "email-invalido");
        var input = new ProcessVideoErrorInput(
                "video.mp4", UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), 5, 50.00, Instant.now()
        );

        // Act
        var ex = assertThrows(UserException.class,
                () -> useCase.createEmailNotificationErrorProcess(user, input));

        // Assert
        assertEquals("Endereço de email do usuário é inválido", ex.getMessage());
    }
}