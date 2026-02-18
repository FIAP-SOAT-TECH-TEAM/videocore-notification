package com.soat.fiap.videocore.notification.core.interfaceadapters.mapper;

import org.mapstruct.Mapper;

import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoErrorInput;
import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoStatusUpdateInput;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoErrorPayload;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoStatusUpdatePayload;

/**
 * Mapper responsável pela conversão entre payloads de eventos e outros objetos.
 */
@Mapper(componentModel = "spring")
public interface EventMapper {
	/**
	 * Converte o payload de atualização de status do vídeo em objeto de entrada da
	 * aplicação.
	 *
	 * @param payload
	 *            payload do evento recebido
	 * @return objeto de entrada para processamento do status do vídeo
	 */
	ProcessVideoStatusUpdateInput toInput(ProcessVideoStatusUpdatePayload payload);

	/**
	 * Converte o payload de erro do processamento de vídeo em objeto de entrada da
	 * aplicação.
	 *
	 * @param payload
	 *            payload do evento de erro recebido
	 * @return objeto de entrada para tratamento do erro do vídeo
	 */
	ProcessVideoErrorInput toInput(ProcessVideoErrorPayload payload);
}
