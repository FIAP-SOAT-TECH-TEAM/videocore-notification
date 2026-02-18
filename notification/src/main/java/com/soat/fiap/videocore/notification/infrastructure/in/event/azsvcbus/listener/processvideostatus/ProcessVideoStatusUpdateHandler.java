package com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.listener.processvideostatus;

import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.fiap.videocore.notification.common.observability.log.CanonicalContext;
import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.interfaceadapters.controller.ProcessVideoStatusUpdateController;
import com.soat.fiap.videocore.notification.infrastructure.common.exceptions.azure.svcbus.ServiceBusSerializationException;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoStatusUpdatePayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Handler que processa mensagens de atualização de status de vídeo recebidas do
 * Service Bus.
 */
@Component @RequiredArgsConstructor @Slf4j
public class ProcessVideoStatusUpdateHandler {

	private final ObjectMapper objectMapper;
	private final ProcessVideoStatusUpdateController controller;

	/**
	 * Desserializa a mensagem recebida e envia para o controller.
	 *
	 * @param context
	 *            contexto da mensagem recebida
	 */
	@WithSpan(name = "listener.process.video.status.update.event")
	public void handleMessage(ServiceBusReceivedMessageContext context) {
		try {
			var rawBody = context.getMessage().getBody().toString();
			CanonicalContext.add("event_object_string", rawBody);

			var payload = objectMapper.readValue(rawBody, ProcessVideoStatusUpdatePayload.class);
			CanonicalContext.add("event_object", payload);

			controller.processVideoStatusUpdate(payload);

			log.info("request_completed");
		} catch (JsonProcessingException e) {
			log.error("request_error", e);

			throw new ServiceBusSerializationException("Erro ao desserializar evento", e);
		} catch (Exception e) {
			log.error("request_error", e);

			throw e;
		} finally {
			CanonicalContext.clear();
		}
	}
}
