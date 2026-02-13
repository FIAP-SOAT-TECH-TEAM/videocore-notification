package com.soat.fiap.videocore.notification.core.interfaceadapters.gateway;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.infrastructure.common.source.VideoDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Gateway responsável por abstrair o acesso a vídeos armazenados externamente.
 * Atua como intermediário entre a camada de aplicação e a fonte de dados.
 */
@Component
@RequiredArgsConstructor
public class VideoGateway {

    private final VideoDataSource videoDataSource;

    /**
     * Recupera a url de download para as imagens capturadas de um dado vídeo
     *
     * @param userId ID do usuário responsável por encaminhar o vídeo
     * @param requestId ID da requisição
     * @param videoName Nome do vídeo
     * @param expirationMinuteTime Minutos de expiração para a URL de download
     *
     * @return a URl para download das imagens do vídeo
     */
    @WithSpan(name = "gateway.get.video.image.download.url")
    public String getVideoImagesDownloadUrl(String userId, String requestId, String videoName, long expirationMinuteTime) {
        return videoDataSource.getVideoImagesDownloadUrl(userId, requestId, videoName, expirationMinuteTime);
    }
}