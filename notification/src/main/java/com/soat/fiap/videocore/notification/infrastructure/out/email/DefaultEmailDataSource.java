package com.soat.fiap.videocore.notification.infrastructure.out.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.soat.fiap.videocore.notification.infrastructure.common.source.EmailDataSource;
import com.soat.fiap.videocore.notification.infrastructure.out.email.exceptions.EmailException;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

/**
 * Implementação padrão de {@link EmailDataSource}, baseada em SMTP, responsável
 * por enviar e-mails para o usuário autenticado.
 */
@Component @RequiredArgsConstructor @ConditionalOnProperty(prefix = "spring.mail", name = "host")
public class DefaultEmailDataSource implements EmailDataSource {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.from}")
	private String from;

	/**
	 * Envia um e-mail utilizando {@link jakarta.mail.internet.MimeMessage} com
	 * suporte a HTML.
	 *
	 * <p>
	 * Define remetente (quando configurado), destinatário, assunto e corpo da
	 * mensagem. O envio é realizado por meio do {@code mailSender} configurado na
	 * aplicação.
	 * </p>
	 *
	 * @param subject
	 *            assunto do e-mail
	 * @param recipient
	 *            destinatário da mensagem
	 * @param body
	 *            conteúdo do e-mail (HTML habilitado)
	 * @throws EmailException
	 *             caso ocorra falha na criação ou envio da mensagem
	 */
	@Override
	public void sendEmail(String subject, String recipient, String body) {
		try {
			var message = mailSender.createMimeMessage();
			var helper = new MimeMessageHelper(message, true, "UTF-8");

			if (from != null && !from.isEmpty())
				helper.setFrom(from);

			helper.setTo(recipient);
			helper.setSubject(subject);
			helper.setText(body, true);

			mailSender.send(message);
		} catch (MessagingException e) {
			throw new EmailException("Falha ao enviar e-mail", e);
		}
	}
}
