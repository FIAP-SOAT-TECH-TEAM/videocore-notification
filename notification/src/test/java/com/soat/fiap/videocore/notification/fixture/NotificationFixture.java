package com.soat.fiap.videocore.notification.fixture;

import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoErrorInput;
import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoStatusUpdateInput;
import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.core.domain.vo.Message;
import com.soat.fiap.videocore.notification.core.domain.vo.Recipient;
import com.soat.fiap.videocore.notification.core.domain.vo.RecipientName;
import com.soat.fiap.videocore.notification.core.domain.vo.Subject;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;

import java.time.Instant;
import java.util.UUID;

/**
 * Fixture para criação de instâncias válidas de {@link Notification}
 * para uso em testes unitários.
 */
public final class NotificationFixture {

    private NotificationFixture() {
    }

    /**
     * Cria uma instância válida de {@link RecipientName} com um nome padrão.
     * @return um objeto de nome do destinatário.
     */
    public static RecipientName validRecipientName() {
        return new RecipientName("João Silva");
    }

    /**
     * Cria uma instância válida de {@link Subject} para preenchimento de e-mail.
     * @return um objeto de assunto da notificação.
     */
    public static Subject validSubject() {
        return new Subject("Assunto válido");
    }

    /**
     * Cria uma instância válida de {@link Recipient} contendo um e-mail estruturado.
     * @return um objeto de destinatário com e-mail válido.
     */
    public static Recipient validRecipient() {
        return new Recipient("joao@email.com");
    }

    /**
     * Cria uma instância válida de {@link Message} com o corpo do texto.
     * @return um objeto de mensagem com conteúdo textual.
     */
    public static Message validMessage() {
        return new Message("Mensagem válida");
    }

    /**
     * Cria uma instância completa e válida de {@link Notification} utilizando
     * os métodos auxiliares desta fixture.
     * @return uma entidade de notificação pronta para uso em testes.
     */
    public static Notification validNotification() {
        return new Notification(
                validRecipientName(),
                validSubject(),
                validRecipient(),
                validMessage()
        );
    }

    /**
     * Cria um {@link UserDTO} válido para uso nos controllers.
     */
    public static UserDTO user() {
        return new UserDTO(
                "user-id",
                "João Silva",
                "joao@email.com"
        );
    }

    /**
     * Cria uma instância completa e válida de {@link Notification}.
     *
     * @return notificação pronta para envio.
     */
    public static Notification notification() {
        return new Notification(
                validRecipientName(),
                validSubject(),
                validRecipient(),
                validMessage()
        );
    }

    /**
     * Cria um {@link ProcessVideoStatusUpdateInput} representando
     * o início do processamento do vídeo (percentual igual a 0).
     *
     * @return payload de início de processamento.
     */
    public static ProcessVideoStatusUpdateInput notificationInputStarted() {
        return new ProcessVideoStatusUpdateInput(
                "video.mp4",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                0L,
                1L,
                0D,
                Instant.now(),
                false
        );
    }

    /**
     * Cria um {@link ProcessVideoStatusUpdateInput} representando
     * a finalização do processamento do vídeo (percentual igual a 100).
     *
     * @return payload de finalização de processamento.
     */
    public static ProcessVideoStatusUpdateInput notificationInputFinished() {
        return new ProcessVideoStatusUpdateInput(
                "video.mp4",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                10L,
                1L,
                100D,
                Instant.now(),
                false
        );
    }

    /**
     * Cria um {@link ProcessVideoErrorInput} representando
     * um evento de erro durante o processamento do vídeo.
     *
     * @return payload de processamento com erro.
     */
    public static ProcessVideoErrorInput notificationErrorInput() {
        return new ProcessVideoErrorInput(
                "video.mp4",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                5L,
                50D,
                Instant.now()
        );
    }

    /**
     * Cria um {@link ProcessVideoStatusUpdateInput} representando
     * um evento de erro durante o processamento do vídeo.
     *
     * @return payload de processamento com erro.
     */
    public static ProcessVideoStatusUpdateInput notificationInputWithError() {
        return new ProcessVideoStatusUpdateInput(
                "video.mp4",
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                5,
                5,
                50D,
                Instant.now(),
                true
        );
    }


}