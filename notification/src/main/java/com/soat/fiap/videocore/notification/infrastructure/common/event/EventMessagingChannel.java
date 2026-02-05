package com.soat.fiap.videocore.notification.infrastructure.common.event;

/**
 * Centraliza os nomes dos canais de mensageria (filas, tópicos e subscriptions).
 */
public final class EventMessagingChannel {

    /**
     * Fila principal de processamento de eventos - DLQ.
     * <p>Responsável pelo fluxo de erros no processamento do vídeo.</p>
     */
    public static final String PROCESS_QUEUE_DLQ = "process.queue/$DeadLetterQueue";

    /**
     * Tópico de status do processamento.
     * <p>Publica atualizações de estado dos processos.</p>
     */
    public static final String PROCESS_STATUS_TOPIC = "process.status.topic";

    /**
     * Fila principal para processamentos de erro no processamento de um video.
     * <p>Responsável pelo fluxo de erros no processamento do vídeo.</p>
     */
    public static final String PROCESS_ERROR_QUEUE = "process.error.queue";

    /**
     * Assinatura do Tópico de status do processamento.
     * <p>Microsserviço de reports.</p>
     */
    public static final String REPORTS_PROCESS_STATUS_TOPIC_SUBSCRIPTION = "reports.process.status.topic.subscription";
}