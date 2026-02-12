package com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.listener.processvideostatus;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Listener que inicia o processamento de mensagens de atualização de status de vídeo após a inicialização do bean.
 */
@RequiredArgsConstructor
@Component
public class ProcessVideoStatusUpdateListener {

    private final ServiceBusProcessorClient processVideoStatusUpdate;

    @PostConstruct
    public void run() {
        processVideoStatusUpdate.start();
    }
}