package com.soat.fiap.videocore.notification.infrastructure.common.source;

/**
 * Fonte de dados para obtenção de um vídeo.
 */
public interface VideoDataSource {

    /**
     * Obtém a url de download para as imagens capturadas de um dado vídeo
     *
     * @param userId ID do usuário responsável por encaminhar o vídeo
     * @param requestId ID da requisição
     * @param videoName Nome do vídeo
     * @param expirationMinuteTime Minutos de expiração para a URL de download
     *
     * @return a URl para download das imagens do vídeo, ou nulo caso o arquivo de imagens não exista
     */
    String getVideoImagesDownloadUrl(String userId, String requestId, String videoName, long expirationMinuteTime);
}