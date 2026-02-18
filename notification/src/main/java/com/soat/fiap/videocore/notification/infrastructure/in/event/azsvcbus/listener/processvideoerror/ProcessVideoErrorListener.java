package com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.listener.processvideoerror;

import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * Listener que inicia o processamento de mensagens de erro no processamento do
 * vídeo após a inicialização do bean.
 */
@RequiredArgsConstructor @Component
public class ProcessVideoErrorListener {

	private final ServiceBusProcessorClient processVideoError;

	@PostConstruct
	public void run() {
		processVideoError.start();
	}
}
