package com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.listener;

import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.spring.messaging.servicebus.implementation.core.annotation.ServiceBusListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.fiap.videocore.notification.common.observability.log.CanonicalContext;
import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.interfaceadapters.controller.ProcessVideoErrorController;
import com.soat.fiap.videocore.notification.infrastructure.common.event.EventMessagingChannel;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoErrorPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Listener responsável por consumir eventos da fila de erro no processamento do vídeo.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessVideoErrorListener {

    private final ObjectMapper objectMapper;
    private final ProcessVideoErrorController processVideoErrorController;

    /**
     * Consome mensagens da fila {@link EventMessagingChannel#PROCESS_ERROR_QUEUE}.
     *
     * @param message Wrapper da mensagem recebida
     */
    @WithSpan(name = "listener.process.video.error.event")
    @ServiceBusListener(destination = EventMessagingChannel.PROCESS_ERROR_QUEUE)
    public void processEvent(ServiceBusReceivedMessage message) throws Exception {
        try {
            var rawBody = message.getBody().toString();
            CanonicalContext.add("event_object_string", rawBody);

            var body = objectMapper.readValue(rawBody, ProcessVideoErrorPayload.class);
            CanonicalContext.add("event_object", body);

            processVideoErrorController.processVideoError(body);

            log.info("request_completed");
        } finally {
            CanonicalContext.clear();
        }
    }
}