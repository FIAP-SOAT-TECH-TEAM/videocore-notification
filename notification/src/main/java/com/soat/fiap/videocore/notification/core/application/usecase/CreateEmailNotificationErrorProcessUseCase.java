package com.soat.fiap.videocore.notification.core.application.usecase;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoErrorInput;
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
 * quando ocorre um erro no processamento de um vídeo.
 */
@Component
@RequiredArgsConstructor
public class CreateEmailNotificationErrorProcessUseCase {

    /**
     * Cria a notificação de e-mail informando que ocorreu um erro durante o processamento do vídeo.
     *
     * @param userDTO dados do usuário destinatário
     * @param input dados do processamento do vídeo
     *
     * @return notificação de e-mail construída
     */
    @WithSpan(name = "usecase.create.notification.error.process")
    public Notification createEmailNotificationErrorProcess(UserDTO userDTO, ProcessVideoErrorInput input) {

        if (userDTO == null || input == null)
            throw new NotificationException("As informações do usuário ou do processamento do vídeo não podem ser nulas para criação da notificação");

        var recipientName = new RecipientName(userDTO.name());
        var recipient = new Recipient(userDTO.email());

        var frameCutMinutes = input.frameCutMinutes();
        var percentStatusProcess = input.percentStatusProcess();
        var videoName = input.videoName();
        var requestId = input.requestId();
        var reportTime = input.reportTime();

        var subject = new Subject("🙁 O processamento do seu vídeo não pode ser completado");

        var messageText =
                "<div style=\"color:#ff4d4f; font-family:Arial, sans-serif;\">" +
                        "<p>👋 <strong>Olá, " + recipientName.value() + "!</strong></p>" +
                        "<p>❌ O processamento do vídeo <strong>" + videoName + "</strong> encontrou um erro.</p>" +
                        "<p>📊 <strong>Percentual processado:</strong> " + percentStatusProcess + "%</p>" +
                        "<p>⏱️ <strong>Intervalo de captura de imagens:</strong> " + frameCutMinutes + " minuto(s)</p>" +
                        "<p>📌 <strong>Requisição:</strong> " + requestId +
                        " <span style=\"font-size:12px;\">(use este identificador para consultar o administrador e resolver o problema)</span></p>" +
                        "<p>⏰ <strong>Ocorrência do erro:</strong> " + reportTime + "</p>" +
                        "<p>🔄 Você pode tentar fazer o upload do vídeo novamente a qualquer momento.</p>" +
                        "</div>";

        var message = new Message(messageText);

        return new Notification(recipientName, subject, recipient, message);
    }
}