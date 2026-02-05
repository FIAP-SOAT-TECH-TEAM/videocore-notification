package com.soat.fiap.videocore.notification.core.domain.exceptions;

/**
 * Exceção lançada quando a url de download do arquivo de imagens de um vídeo processado for nula ou vazia
 */
public class VideoImageDownloadUrlNotFoundException extends RuntimeException {

	public VideoImageDownloadUrlNotFoundException(String message) {
		super(message);
	}

	public VideoImageDownloadUrlNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

    public VideoImageDownloadUrlNotFoundException(String message, Object... args) {
        super(String.format(message, args));
    }
}