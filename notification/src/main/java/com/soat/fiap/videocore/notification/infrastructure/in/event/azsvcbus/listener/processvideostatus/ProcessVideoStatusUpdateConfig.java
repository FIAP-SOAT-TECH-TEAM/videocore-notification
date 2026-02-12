package com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.listener.processvideostatus;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.soat.fiap.videocore.notification.infrastructure.common.config.azure.svcbus.ServiceBusConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configura o processor client para consumir eventos de atualização de status de vídeo do Service Bus.
 */
@Configuration
@RequiredArgsConstructor
public class ProcessVideoStatusUpdateConfig {

    private final ProcessVideoStatusUpdateHandler handler;

    /**
     * Cria um ServiceBusProcessorClient para o tópico de status de vídeo.
     *
     * @param builder builder do Service Bus
     * @return client configurado
     */
    @Bean
    public ServiceBusProcessorClient processVideoStatusUpdate(ServiceBusClientBuilder builder) {
        return builder.processor()
                .topicName(ServiceBusConfig.PROCESS_STATUS_TOPIC)
                .subscriptionName(ServiceBusConfig.NOTIFICATION_PROCESS_STATUS_TOPIC_SUBSCRIPTION)
                .processMessage(handler::handleMessage)
                .processError(context -> {})
                .buildProcessorClient();
    }
}