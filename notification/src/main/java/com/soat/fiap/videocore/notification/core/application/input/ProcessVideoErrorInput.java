package com.soat.fiap.videocore.notification.core.application.input;

import java.time.Instant;

/**
 * Representa um DTO de entrada da aplicação (Application Layer), contendo
 * detalhes sobre o erro no processamento de um vídeo.
 *
 * @param videoName
 *            Nome do vídeo.
 * @param userId
 *            Identificador do usuário dono do vídeo.
 * @param requestId
 *            Identificador da requisição de processamento.
 * @param traceId
 *            Identificador de rastreio (observabilidade).
 * @param frameCutMinutes
 *            Intervalo de corte de frames em minutos.
 * @param percentStatusProcess
 *            Percentual do vídeo já processado.
 * @param reportTime
 *            Momento em que o erro foi detectado.
 */
public record ProcessVideoErrorInput(String videoName, String userId, String requestId, String traceId,
		long frameCutMinutes, Double percentStatusProcess, Instant reportTime) {

}
