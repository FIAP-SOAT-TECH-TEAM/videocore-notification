package com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.listener;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.spring.messaging.servicebus.implementation.core.annotation.ServiceBusListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.fiap.videocore.notification.common.observability.log.CanonicalContext;
import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.interfaceadapters.controller.ProcessVideoStatusUpdateController;
import com.soat.fiap.videocore.notification.infrastructure.common.event.EventMessagingChannel;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoStatusUpdatePayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Listener responsável por consumir eventos da fila de atualização do status de processamento de um vídeo.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessVideoStatusUpdateListener {

    private final ObjectMapper objectMapper;
    private final ProcessVideoStatusUpdateController processVideoStatusUpdateController;

    /**
     * Consome mensagens da fila {@link EventMessagingChannel#PROCESS_STATUS_TOPIC}.
     *
     * @param message Wrapper da mensagem recebida
     */
    @WithSpan(name = "listener.process.video.status.update.event")
    @ServiceBusListener(destination = EventMessagingChannel.PROCESS_STATUS_TOPIC,
            group = EventMessagingChannel.NOTIFICATION_PROCESS_STATUS_TOPIC_SUBSCRIPTION)
    public void processEvent(ServiceBusReceivedMessage message) throws Exception {
        try {
            var rawBody = message.getBody().toString();
            CanonicalContext.add("event_object_string", rawBody);

            var body = objectMapper.readValue(rawBody, ProcessVideoStatusUpdatePayload.class);
            CanonicalContext.add("event_object", body);

            processVideoStatusUpdateController.processVideoStatusUpdate(body);

            log.info("request_completed");
        } finally {
            CanonicalContext.clear();
        }
    }
}