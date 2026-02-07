package com.soat.fiap.videocore.notification.unit.usecase;

import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoErrorInput;
import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoStatusUpdateInput;
import com.soat.fiap.videocore.notification.core.application.usecase.CreateEmailNotificationFinishedProcessUseCase;
import com.soat.fiap.videocore.notification.core.domain.exceptions.UserException;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do caso de uso {@link CreateEmailNotificationFinishedProcessUseCase}.
 */
class CreateEmailNotificationFinishedProcessUseCaseTest {

    private final CreateEmailNotificationFinishedProcessUseCase useCase =
            new CreateEmailNotificationFinishedProcessUseCase();

    @Test
    void shouldCreateNotificationWhenInputIsValid() {
        // arrange
        var user = new UserDTO("1", "Maria", "maria@email.com");
        var input = new ProcessVideoStatusUpdateInput(
                "video.mp4", UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1, 10.0, Instant.now(), false
        );

        // act
        Notification notification =
                useCase.createEmailNotificationFinishedProcess(user, input, "http://download");

        // assert
        assertNotNull(notification);
        assertEquals("Maria", notification.getRecipientName());
        assertEquals("maria@email.com", notification.getRecipient());
        assertTrue(notification.getMessage().contains("Download"));
    }

    @Test
    void shouldThrowExceptionWhenDownloadUrlIsIrrelevantButUserIsInvalid() {
        // arrange
        var user = new UserDTO("1", "Maria", "email-invalido");
        var input = new ProcessVideoStatusUpdateInput(
                "video.mp4", UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1, 10.0, Instant.now(), false
        );

        // act
        var ex = assertThrows(UserException.class,
                () -> useCase.createEmailNotificationFinishedProcess(user, input, "url"));

        // assert
        assertEquals("Endereço de email do usuário é inválido", ex.getMessage());
    }
}