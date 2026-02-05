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
 * informando a finalização do processamento de um vídeo.
 */
@Component
@RequiredArgsConstructor
public class CreateEmailNotificationFinishedProcessUseCase {

    /**
     * Cria a notificação de e-mail de finalização do processamento do vídeo.
     *
     * @param userDTO dados do usuário destinatário
     * @param input dados do processamento do vídeo
     * @param downloadUrl URL de download para as imagens do vídeo
     * @return notificação de e-mail construída
     */
    @WithSpan(name = "usecase.create.notification.finished.process")
    public Notification createEmailNotificationFinishedProcess(UserDTO userDTO, ProcessVideoStatusUpdateInput input, String downloadUrl) {

        if (userDTO == null || input == null)
            throw new NotificationException("As informações do usuário ou do processamento do vídeo não podem ser nulas para criação da notificação");

        var recipientName = new RecipientName(userDTO.name());
        var recipient = new Recipient(userDTO.email());

        var videoName = input.videoName();
        var frameCutMinutes = input.frameCutMinutes();
        var requestId = input.requestId();
        var reportTime = input.reportTime();

        var subject = new Subject("😎 Seu vídeo terminou de ser processado");

        var messageText =
                "<div style=\"color:#1e90ff; font-family:Arial, sans-serif;\">" +
                        "<p>👋 <strong>Olá, " + recipientName.value() + "!</strong></p>" +
                        "<p>✅ O processamento do vídeo <strong>" + videoName + "</strong> foi finalizado com sucesso.</p>" +
                        "<p>As imagens foram capturadas a cada: <strong>" + frameCutMinutes + " minuto(s)</strong> 💙</p>" +
                        "<p>📌 <strong>Requisição:</strong> " + requestId +
                        " <span style=\"font-size:12px;\">(use este identificador para consultar relatórios ou falar com o suporte)</span></p>" +
                        "<p>⏰ <strong>Fim do processamento:</strong> " + reportTime + "</p>" +
                        "<p>📥 <strong>Download das imagens:</strong> <a href=\"" + downloadUrl + "\">Clique aqui para baixar</a></p>" +
                        "</div>";

        var message = new Message(messageText);

        return new Notification(recipientName, subject, recipient, message);
    }
}