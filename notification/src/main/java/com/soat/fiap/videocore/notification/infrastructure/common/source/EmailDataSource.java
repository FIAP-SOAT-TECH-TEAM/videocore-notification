package com.soat.fiap.videocore.notification.infrastructure.common.source;

/**
 * Fonte de dados responsável pelo envio de e-mails.
 */
public interface EmailDataSource {

	/**
	 * Envia um e-mail simples (texto ou HTML) para um destinatário.
     *
     * @param subject
     *            o assunto do e-mail
	 * @param recipient
	 *            destinatário do e-mail.
	 * @param body
	 *            o corpo do e-mail (pode ser HTML)
	 * @throws RuntimeException
	 *             se ocorrer erro ao enviar o e-mail
	 */
	void sendEmail(String subject, String recipient, String body);
}
