package com.soat.fiap.videocore.notification.core.interfaceadapters.controller;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.application.usecase.*;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.interfaceadapters.mapper.EventMapper;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoStatusUpdatePayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Controller responsável por orquestrar reportes de atualizações no status de processamento de um vídeo.
 */
@Component
@RequiredArgsConstructor
public class ProcessVideoStatusUpdateController {

    private final GetUserByIdUseCase getUserByIdUseCase;
    private final CreateEmailNotificationStartedProcessUseCase createEmailNotificationStartedProcessUseCase;
    private final CreateEmailNotificationFinishedProcessUseCase createEmailNotificationFinishedProcessUseCase;
    private final GetVideoImagesDownloadUrlUseCase getVideoImagesDownloadUrlUseCase;
    private final SendEmailUseCase sendEmailUseCase;
    private final EventMapper eventMapper;

    /**
     * Processa a atualização no status de processamento de um vídeo através do payload de evento: {@link ProcessVideoStatusUpdatePayload}.
     *
     * @param payload Payload de atualização de status do vídeo.
     */
    @WithSpan(name = "controller.process.video.status.update")
    public void processVideoStatusUpdate(ProcessVideoStatusUpdatePayload payload) {
        var user = getUserByIdUseCase.getUserById(payload.userId());
        var input = eventMapper.toInput(payload);

        Notification notification = null;

        if (input.percentStatusProcess() == 0) {
            notification = createEmailNotificationStartedProcessUseCase.createEmailNotificationStartedProcess(user, input);
        }
        else if (input.percentStatusProcess() == 100) {
            var downloadUrl = getVideoImagesDownloadUrlUseCase.getVideoImagesDownloadUrl(payload.userId(), payload.requestId(), payload.videoName());
            notification = createEmailNotificationFinishedProcessUseCase.createEmailNotificationFinishedProcess(user, input, downloadUrl);
        }

        if (notification != null) {
            sendEmailUseCase.sendEmail(notification);
        }

    }
}