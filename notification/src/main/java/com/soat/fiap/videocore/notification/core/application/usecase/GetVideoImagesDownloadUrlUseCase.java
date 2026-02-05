package com.soat.fiap.videocore.notification.core.application.usecase;

import com.soat.fiap.videocore.notification.common.observability.log.CanonicalContext;
import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.domain.exceptions.VideoException;
import com.soat.fiap.videocore.notification.core.domain.exceptions.VideoImageDownloadUrlNotFoundException;
import com.soat.fiap.videocore.notification.core.interfaceadapters.gateway.VideoGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Caso de uso responsável por recuperar a URL de download das imagens
 * capturadas de um vídeo.
 */
@Component
@RequiredArgsConstructor
public class GetVideoImagesDownloadUrlUseCase {

    private final VideoGateway videoGateway;

    /**
     * Recupera a URL de download das imagens de um vídeo a partir das chaves de negócio, utilizando um tempo de expiração de 30 minutos.
     *
     * @param userId identificador do usuário
     * @param requestId identificador da requisição
     * @param videoName nome do vídeo
     *
     * @return URL para download das imagens do vídeo
     */
    @WithSpan(name = "usecase.get.video.images.download.url")
    public String getVideoImagesDownloadUrl(String userId, String requestId, String videoName) {
        CanonicalContext.add("user_id", userId);
        CanonicalContext.add("request_id", requestId);
        CanonicalContext.add("video_name", videoName);

        if (userId == null || userId.isBlank()) {
            throw new VideoException("userId não pode ser nulo ou vazio para pesquisa de URL de download das imagens");
        }

        if (requestId == null || requestId.isBlank()) {
            throw new VideoException("requestId não pode ser nulo ou vazio para pesquisa de URL de download das imagens");
        }

        if (videoName == null || videoName.isBlank()) {
            throw new VideoException("videoName não pode ser nulo ou vazio para pesquisa de URL de download das imagens");
        }

        var expirationMinuteTime = 30L;

        CanonicalContext.add("expiration_minute_time", expirationMinuteTime);

        var downloadUrl = videoGateway.getVideoImagesDownloadUrl(userId, requestId, videoName, expirationMinuteTime);

        CanonicalContext.add("download_url", downloadUrl);

        if (downloadUrl == null || downloadUrl.isBlank())
            throw new VideoImageDownloadUrlNotFoundException("URL de download do arquivo de imagens do vídeo informado não encontrada. UserID: %s, RequestID: %s e VideoName: %s", userId, requestId, videoName);

        return downloadUrl;
    }
}