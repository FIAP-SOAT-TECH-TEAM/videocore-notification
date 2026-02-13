package com.soat.fiap.videocore.notification.core.domain.model;

import com.soat.fiap.videocore.notification.core.domain.vo.Message;
import com.soat.fiap.videocore.notification.core.domain.vo.Recipient;
import com.soat.fiap.videocore.notification.core.domain.vo.RecipientName;
import com.soat.fiap.videocore.notification.core.domain.vo.Subject;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

/**
 * Objeto de domínio que representa uma notificação do sistema.
 */
@Getter
public class Notification {

    /**
     * Assunto da notificação.
     */
    private final RecipientName recipientName;

    /**
     * Assunto da notificação.
     */
    private final Subject subject;

    /**
     * Destinatário da notificação.
     */
    private final Recipient recipient;

    /**
     * Mensagem da notificação.
     */
    private final Message message;

    public Notification(RecipientName recipientName, Subject subject, Recipient recipient, Message message) {
        this.recipientName = recipientName;
        this.subject = subject;
        this.recipient = recipient;
        this.message = message;
        validate();
    }

    private void validate() {
        Objects.requireNonNull(recipientName, "recipientName não pode ser nulo");
        Objects.requireNonNull(subject, "subject não pode ser nulo");
        Objects.requireNonNull(recipient, "recipient não pode ser nulo");
        Objects.requireNonNull(message, "message não pode ser nulo");
    }

    /**
     * Retorna o nome do destinatário da notificação.
     *
     * @return nome do destinatári associado à notificação
     */
    public String getRecipientName() {
        return recipientName.value();
    }

    /**
     * Retorna o assunto da notificação.
     *
     * @return assunto associado à notificação
     */
    public String getSubject() {
        return subject.value();
    }

    /**
     * Retorna o destinatário da notificação.
     *
     * @return destinatário associado à notificação
     */
    public String getRecipient() {
        return recipient.value();
    }

    /**
     * Retorna a mensagem da notificação.
     *
     * @return mensagem associada à notificação
     */
    public String getMessage() {
        return message.value();
    }

}