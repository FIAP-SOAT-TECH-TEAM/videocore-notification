package com.soat.fiap.videocore.notification.core.interfaceadapters.mapper;

import com.soat.fiap.videocore.notification.core.application.input.ProcessVideoStatusUpdateInput;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoStatusUpdatePayload;
import org.mapstruct.Mapper;

/**
 * Mapper responsável pela conversão entre payloads de eventos e outros objetos.
 */
@Mapper(componentModel = "spring")
public interface EventMapper {
    /**
     * Converte o payload de atualização de status do vídeo em objeto de entrada da aplicação.
     *
     * @param payload payload do evento recebido
     * @return objeto de entrada para processamento do status do vídeo
     */
    ProcessVideoStatusUpdateInput toInput(ProcessVideoStatusUpdatePayload payload);
}