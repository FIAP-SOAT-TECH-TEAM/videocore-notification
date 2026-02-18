package com.soat.fiap.videocore.notification.core.interfaceadapters.controller;

import org.springframework.stereotype.Component;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.application.usecase.*;
import com.soat.fiap.videocore.notification.core.interfaceadapters.mapper.EventMapper;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoErrorPayload;

import lombok.RequiredArgsConstructor;

/**
 * Controller responsável por orquestrar o processamento de erro no
 * processamento de um vídeo.
 */
@Component @RequiredArgsConstructor
public class ProcessVideoErrorController {

	private final GetUserByIdUseCase getUserByIdUseCase;
	private final CreateEmailNotificationErrorProcessUseCase createEmailNotificationErrorProcessUseCase;
	private final SendEmailUseCase sendEmailUseCase;
	private final EventMapper eventMapper;

	/**
	 * Processa o erro no processamento de um vídeo através do payload de evento:
	 * {@link ProcessVideoErrorPayload}.
	 *
	 * @param payload
	 *            Payload de erro no processamento de um vídeo.
	 */
	@WithSpan(name = "controller.process.video.error")
	public void processVideoError(ProcessVideoErrorPayload payload) {
		var user = getUserByIdUseCase.getUserById(payload.userId());
		var input = eventMapper.toInput(payload);

		var notification = createEmailNotificationErrorProcessUseCase.createEmailNotificationErrorProcess(user, input);

		sendEmailUseCase.sendEmail(notification);
	}
}
