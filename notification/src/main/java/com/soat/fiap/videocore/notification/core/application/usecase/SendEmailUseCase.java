package com.soat.fiap.videocore.notification.core.application.usecase;

import org.springframework.stereotype.Component;

import com.soat.fiap.videocore.notification.common.observability.log.CanonicalContext;
import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.interfaceadapters.gateway.EmailGateway;

import lombok.RequiredArgsConstructor;

/**
 * Caso de uso responsável pelo envio de notificações por e-mail.
 */
@Component @RequiredArgsConstructor
public class SendEmailUseCase {

	/**
	 * Gateway de envio de e-mails.
	 */
	private final EmailGateway emailGateway;

	/**
	 * Envia uma notificação por e-mail.
	 *
	 * @param notification
	 *            notificação a ser enviada
	 */
	@WithSpan(name = "usecase.send.email")
	public void sendEmail(Notification notification) {
		if (notification == null)
			throw new NotificationException("A notificação não pode ser nula para o disparo de emails");

		var subject = notification.getSubject();
		var recipient = notification.getRecipient();
		var message = notification.getMessage();

		CanonicalContext.add("email_subject", subject);
		CanonicalContext.add("email_recipient", recipient);
		CanonicalContext.add("email_message", message);

		emailGateway.sendEmail(subject, recipient, message);
	}
}
