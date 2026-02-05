package com.soat.fiap.videocore.notification.core.interfaceadapters.controller;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.application.usecase.CreateEmailNotificationStartedProcessUseCase;
import com.soat.fiap.videocore.notification.core.application.usecase.GetUserByIdUseCase;
import com.soat.fiap.videocore.notification.core.application.usecase.SendEmailUseCase;
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

        if (input.percentStatusProcess() == 0) {
            var notification = createEmailNotificationStartedProcessUseCase.createEmailNotificationStartedProcess(user, input);
            sendEmailUseCase.sendEmail(notification);
        }

    }
}