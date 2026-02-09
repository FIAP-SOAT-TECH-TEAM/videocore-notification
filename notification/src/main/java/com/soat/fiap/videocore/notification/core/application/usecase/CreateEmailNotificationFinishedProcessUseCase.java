package com.soat.fiap.videocore.notification.core.application.usecase;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoStatusUpdateInput;
import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.exceptions.UserException;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.domain.vo.Message;
import com.soat.fiap.videocore.notification.core.domain.vo.Recipient;
import com.soat.fiap.videocore.notification.core.domain.vo.RecipientName;
import com.soat.fiap.videocore.notification.core.domain.vo.Subject;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

        var emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(userDTO.email()))
            throw new UserException("Endereço de email do usuário é inválido");

        var recipientName = new RecipientName(userDTO.name());
        var recipient = new Recipient(userDTO.email());

        var videoName = input.videoName();
        var frameCutMinutes = input.frameCutMinutes();
        var requestId = input.requestId();
        var traceId = input.traceId();

        var reportTime = input.reportTime();
        var reportDateTime = ZonedDateTime.ofInstant(reportTime, ZoneId.systemDefault());
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        var formattedReportTime = reportDateTime.format(formatter);

        var subject = new Subject("VideoCore | 😎 Seu vídeo terminou de ser processado");

        var messageText =
                "<div style=\"font-family:Arial, Helvetica, sans-serif; color:#1f2937;\">" +
                        "<div style=\"max-width:600px; margin:0 auto; border:1px solid #e5e7eb; border-radius:8px; overflow:hidden;\">" +
                        "<div style=\"background:linear-gradient(90deg,#93c5fd,#3b82f6,#1e3a8a); " +
                        "background-size:400% 400%; " +
                        "animation:gradientShift 6s ease infinite; " +
                        "padding:16px; color:#ffffff;\">" +
                        "<style>" +
                        "@keyframes gradientShift {" +
                        "0% { background-position:0% 50%; }" +
                        "50% { background-position:100% 50%; }" +
                        "100% { background-position:0% 50%; }" +
                        "}" +
                        "</style>" +
                        "<h2 style=\"margin:0; font-size:20px;\">🎉 Seu vídeo foi processado com sucesso</h2>" +
                        "</div>" +
                        "<div style=\"padding:20px;\">" +
                        "<p>Olá, <strong>" + recipientName.value() + "</strong>,</p>" +
                        "<p>O processamento do vídeo <strong>" + videoName + "</strong> foi concluído com sucesso.</p>" +
                        "<p style=\"margin-top:16px;\"><strong>Resumo do processamento:</strong></p>" +
                        "<ul style=\"padding-left:20px;\">" +
                        "<li><strong>⏱️ Intervalo de captura: </strong>" + frameCutMinutes + " minuto(s)</li>" +
                        "<li><strong>🕒 Finalizado em: </strong>" + formattedReportTime + "</li>" +
                        "</ul>" +
                        "<p style=\"margin-top:16px;\">📥 <strong>Acesso às imagens:</strong></p>" +
                        "<p><a href=\"" + downloadUrl + "\" style=\"color:#1d4ed8; text-decoration:none; font-weight:bold;\">Clique aqui para realizar o download</a></p>" +
                        "<p style=\"font-size:12px; color:#6b7280;\">Link válido por 30 minutos.</p>" +
                        "<p style=\"margin-top:16px;\">Em caso de dúvidas, utilize os identificadores abaixo ao falar com o suporte:</p>" +
                        "<p style=\"font-size:13px; color:#374151;\">" +
                        "<strong>Trace ID: </strong>" + traceId + "<br/>" +
                        "<strong>Request ID: </strong>" + requestId +
                        "</p>" +
                        "</div>" +
                        "<div style=\"background-color:#f3f4f6; padding:12px; text-align:center; font-size:12px; color:#6b7280;\">" +
                        "VideoCore - Plataforma de processamento de vídeos 🩵💙" +
                        "</div>" +
                        "</div>" +
                        "</div>";

        var message = new Message(messageText);

        return new Notification(recipientName, subject, recipient, message);
    }
}