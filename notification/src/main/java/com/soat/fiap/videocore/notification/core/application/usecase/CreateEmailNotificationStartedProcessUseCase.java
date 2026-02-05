package com.soat.fiap.videocore.notification.core.application.usecase;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoStatusUpdateInput;
import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.domain.vo.Message;
import com.soat.fiap.videocore.notification.core.domain.vo.Recipient;
import com.soat.fiap.videocore.notification.core.domain.vo.RecipientName;
import com.soat.fiap.videocore.notification.core.domain.vo.Subject;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Caso de uso responsável por criar uma notificação por e-mail
 * informando o início do processamento de um vídeo.
 */
@Component
@RequiredArgsConstructor
public class CreateEmailNotificationStartedProcessUseCase {


    /**
     * Cria a notificação de e-mail de início de processamento do vídeo.
     *
     * @param userDTO dados do usuário destinatário
     * @param input dados do processamento do vídeo
     * @return notificação de e-mail construída
     */
    @WithSpan(name = "usecase.create.notification.started.process")
    public Notification createEmailNotificationStartedProcess(UserDTO userDTO, ProcessVideoStatusUpdateInput input) {

        if (userDTO == null || input == null)
            throw new NotificationException("As informações do usuário ou do processamento do vídeo não podem ser nulas para criação da notificação");

        var recipientName = new RecipientName(userDTO.name());
        var subject = new Subject(userDTO.subject());
        var recipient = new Recipient(userDTO.email());

        var videoName = input.videoName();
        var frameCutMinutes = input.frameCutMinutes();
        var requestId = input.requestId();
        var reportTime = input.reportTime();

        var messageText =
                "<div style=\"color:#1e90ff; font-family:Arial, sans-serif;\">" +
                        "<p>👋 <strong>Olá, " + recipientName.value() + "!</strong></p>" +
                        "<p>🚀 O processamento do vídeo <strong>" + videoName + "</strong> foi iniciado com sucesso.</p>" +
                        "<p>Estamos trabalhando a cada: <strong>" + frameCutMinutes + " minuto(s)</strong> para capturar as imagens 💙</p>" +
                        "<p>📌 <strong>Requisição:</strong> " + requestId +
                        " <span style=\"font-size:12px;\">(use este identificador para acompanhar o status, consultar relatórios ou falar com o suporte)</span></p>" +
                        "<p>⏰ <strong>Início do processamento:</strong> " + reportTime + "</p>" +
                        "</div>";

        var message = new Message(messageText);

        return new Notification(recipientName, subject, recipient, message);
    }
}