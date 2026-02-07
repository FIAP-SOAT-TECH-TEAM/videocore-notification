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

        var emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(userDTO.email()))
            throw new UserException("Endereço de email do usuário é inválido");

        var recipientName = new RecipientName(userDTO.name());
        var recipient = new Recipient(userDTO.email());

        var videoName = input.videoName();
        var frameCutMinutes = input.frameCutMinutes();
        var requestId = input.requestId();

        var reportTime = input.reportTime();
        var reportDateTime = ZonedDateTime.ofInstant(reportTime, ZoneId.systemDefault());
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        var formattedReportTime = reportDateTime.format(formatter);

        var subject = new Subject("🧐 O processamento do seu vídeo começou");

        var messageText =
                "<div style=\"color:#1e90ff; font-family:Arial, sans-serif;\">" +
                        "<p>👋 <strong>Olá, " + recipientName.value() + "!</strong></p>" +
                        "<p>🚀 O processamento do vídeo <strong>" + videoName + "</strong> foi iniciado com sucesso.</p>" +
                        "<p>Estamos trabalhando a cada: <strong>" + frameCutMinutes + " minuto(s)</strong> para capturar as imagens 💙</p>" +
                        "<p>📌 <strong>Requisição:</strong> " + requestId +
                        " <span style=\"font-size:12px;\">(use este identificador para falar com o suporte em caso de dúvidas)</span></p>" +
                        "<p>⏰ <strong>Início do processamento:</strong> " + formattedReportTime + "</p>" +
                        "</div>";

        var message = new Message(messageText);

        return new Notification(recipientName, subject, recipient, message);
    }
}