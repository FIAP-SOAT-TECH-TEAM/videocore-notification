package com.soat.fiap.videocore.notification.infrastructure.out.email;

import com.soat.fiap.videocore.notification.infrastructure.common.source.EmailDataSource;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Implementação padrão de {@link EmailDataSource}, baseada em SMTP, responsável
 * por enviar e-mails para o usuário autenticado.
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.mail", name = "host")
public class DefaultEmailDataSource implements EmailDataSource {

	private final JavaMailSender mailSender;

	@Value("${spring.mail.from}")
	private String from;

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
			throw new RuntimeException("Falha ao enviar e-mail", e);
		}
	}
}
