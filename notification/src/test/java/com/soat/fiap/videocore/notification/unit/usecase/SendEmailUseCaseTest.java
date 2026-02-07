package com.soat.fiap.videocore.notification.unit.usecase;

import com.soat.fiap.videocore.notification.core.application.usecase.SendEmailUseCase;
import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.interfaceadapters.gateway.EmailGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários do caso de uso {@link SendEmailUseCase}.
 */
class SendEmailUseCaseTest {

    private final EmailGateway emailGateway = mock(EmailGateway.class);
    private final SendEmailUseCase useCase = new SendEmailUseCase(emailGateway);

    @Test
    void shouldSendEmailWhenNotificationIsValid() {
        // arrange
        var notification = mock(Notification.class);
        when(notification.getSubject()).thenReturn("subject");
        when(notification.getRecipient()).thenReturn("to@email.com");
        when(notification.getMessage()).thenReturn("message");

        // act
        useCase.sendEmail(notification);

        // assert
        verify(emailGateway).sendEmail("subject", "to@email.com", "message");
    }

    @Test
    void shouldThrowExceptionWhenNotificationIsNull() {
        // arrange / act
        var ex = assertThrows(NotificationException.class,
                () -> useCase.sendEmail(null));

        // assert
        assertEquals(
                "A notificação não pode ser nula para o disparo de emails",
                ex.getMessage()
        );
    }
}
