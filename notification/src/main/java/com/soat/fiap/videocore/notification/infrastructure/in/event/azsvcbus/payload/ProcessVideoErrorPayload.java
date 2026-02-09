package com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload;

import java.time.Instant;

/**
 * Payload que representa um erro no processamento do vídeo.
 *
 * @param videoName             Nome do vídeo.
 * @param userId                Identificador do usuário dono do vídeo.
 * @param requestId             Identificador da requisição de processamento.
 * @param traceId               Identificador de rastreio (observabilidade).
 * @param frameCutMinutes       Intervalo de corte de frames em minutos.
 * @param percentStatusProcess  Percentual do vídeo já processado.
 * @param reportTime            Momento em que o erro foi detectado.
 */
public record ProcessVideoErrorPayload(
        String videoName,
        String userId,
        String requestId,
        String traceId,
        long frameCutMinutes,
        Double percentStatusProcess,
        Instant reportTime
) {

}