package com.soat.fiap.videocore.notification.infrastructure.common.event;

/**
 * Centraliza os nomes dos canais de mensageria (filas, tópicos e subscriptions).
 */
public final class EventMessagingChannel {

    /**
     * Fila principal para processamentos de erro no processamento de um video.
     * <p>Responsável pelo fluxo de erros no processamento do vídeo.</p>
     */
    public static final String PROCESS_ERROR_QUEUE = "process.error.queue";

    /**
     * Tópico de status do processamento.
     * <p>Publica atualizações de estado dos processos.</p>
     */
    public static final String PROCESS_STATUS_TOPIC = "process.status.topic";

    /**
     * Assinatura do Tópico de status do processamento.
     * <p>Microsserviço de notification.</p>
     */
    public static final String NOTIFICATION_PROCESS_STATUS_TOPIC_SUBSCRIPTION = "notification.process.status.topic.subscription";
}