package com.soat.fiap.videocore.notification.unit.controller;

import com.soat.fiap.videocore.notification.core.application.usecase.*;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.interfaceadapters.controller.ProcessVideoStatusUpdateController;
import com.soat.fiap.videocore.notification.core.interfaceadapters.mapper.EventMapper;
import com.soat.fiap.videocore.notification.fixture.NotificationFixture;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoStatusUpdatePayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * Testes unitários do controller {@link ProcessVideoStatusUpdateController}.
 * Valida todos os fluxos possíveis de notificação durante o processamento de vídeo.
 */
@ExtendWith(MockitoExtension.class)
class ProcessVideoStatusUpdateControllerTest {

    @Mock
    private GetUserByIdUseCase getUserByIdUseCase;
    @Mock
    private CreateEmailNotificationStartedProcessUseCase createEmailNotificationStartedProcessUseCase;
    @Mock
    private CreateEmailNotificationFinishedProcessUseCase createEmailNotificationFinishedProcessUseCase;
    @Mock
    private GetVideoImagesDownloadUrlUseCase getVideoImagesDownloadUrlUseCase;
    @Mock
    private SendEmailUseCase sendEmailUseCase;
    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private ProcessVideoStatusUpdateController controller;

    @Test
    void shouldSendStartedProcessNotificationWhenPercentIsZero() {
        // arrange
        var payload = mock(ProcessVideoStatusUpdatePayload.class);
        var user = NotificationFixture.user();
        var input = NotificationFixture.notificationInputStarted();
        var notification = NotificationFixture.notification();

        when(payload.userId()).thenReturn("user-id");
        when(getUserByIdUseCase.getUserById("user-id")).thenReturn(user);
        when(eventMapper.toInput(payload)).thenReturn(input);
        when(createEmailNotificationStartedProcessUseCase.createEmailNotificationStartedProcess(user, input))
                .thenReturn(notification);

        // act
        controller.processVideoStatusUpdate(payload);

        // assert
        verify(sendEmailUseCase).sendEmail(notification);
    }

    @Test
    void shouldSendFinishedProcessNotificationWhenPercentIsHundred() {
        // arrange
        var payload = mock(ProcessVideoStatusUpdatePayload.class);
        var user = NotificationFixture.user();
        var input = NotificationFixture.notificationInputFinished();
        var notification = NotificationFixture.notification();

        when(payload.userId()).thenReturn("user-id");
        when(payload.requestId()).thenReturn("request-id");
        when(payload.videoName()).thenReturn("video.mp4");
        when(getUserByIdUseCase.getUserById("user-id")).thenReturn(user);
        when(eventMapper.toInput(payload)).thenReturn(input);
        when(getVideoImagesDownloadUrlUseCase.getVideoImagesDownloadUrl("user-id", "request-id", "video.mp4"))
                .thenReturn("http://download");
        when(createEmailNotificationFinishedProcessUseCase
                .createEmailNotificationFinishedProcess(user, input, "http://download"))
                .thenReturn(notification);

        // act
        controller.processVideoStatusUpdate(payload);

        // assert
        verify(sendEmailUseCase).sendEmail(notification);
    }

    @Test
    void shouldNotSendEmailWhenNoNotificationIsCreated() {
        // arrange
        var payload = mock(ProcessVideoStatusUpdatePayload.class);
        var user = NotificationFixture.user();
        var input = NotificationFixture.notificationInputWithError();

        when(payload.userId()).thenReturn("user-id");
        when(getUserByIdUseCase.getUserById("user-id")).thenReturn(user);
        when(eventMapper.toInput(payload)).thenReturn(input);

        // act
        controller.processVideoStatusUpdate(payload);

        // assert
        verify(sendEmailUseCase, never()).sendEmail(any(Notification.class));
    }
}