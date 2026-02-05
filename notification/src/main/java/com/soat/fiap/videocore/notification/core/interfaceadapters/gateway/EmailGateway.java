package com.soat.fiap.videocore.notification.core.interfaceadapters.gateway;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.infrastructure.common.source.EmailDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Gateway para envio de e-mails.
 */
@Component
@RequiredArgsConstructor
public class EmailGateway {

	private final EmailDataSource emailDataSource;

	/**
	 * Envia um e-mail para o usuário autenticado.
     *
     * @param subject
     *            o assunto do e-mail
	 * @param recipient
	 *            destinatário do e-mail.
	 * @param body
	 *            o corpo do e-mail (pode ser HTML)
	 */
    @WithSpan(name = "gateway.send.email")
	public void sendEmail(String subject, String recipient, String body) {
		emailDataSource.sendEmail(subject, recipient, body);
	}
}
