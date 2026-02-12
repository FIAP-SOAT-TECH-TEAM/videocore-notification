package com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.listener.processvideoerror;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.soat.fiap.videocore.notification.infrastructure.common.config.azure.svcbus.ServiceBusConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configura o processor client para consumir eventos de erro no processamento do vídeo do Service Bus.
 */
@Configuration
@RequiredArgsConstructor
public class ProcessVideoErrorConfig {

    private final ProcessVideoErrorHandler handler;

    /**
     * Cria um ServiceBusProcessorClient para a fila de erro de processamento.
     *
     * @param builder builder do Service Bus
     * @return client configurado
     */
    @Bean
    public ServiceBusProcessorClient processVideoError(ServiceBusClientBuilder builder) {
        return builder.processor()
                .queueName(ServiceBusConfig.PROCESS_ERROR_QUEUE)
                .processMessage(handler::handleMessage)
                .processError(context -> {})
                .buildProcessorClient();
    }
}