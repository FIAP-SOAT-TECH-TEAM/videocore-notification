package com.soat.fiap.videocore.notification.core.domain.exceptions;

/**
 * Exceção lançada quando um erro ocorre referente a um video
 */
public class VideoException extends RuntimeException {

	public VideoException(String message) {
		super(message);
	}

	public VideoException(String message, Throwable cause) {
		super(message, cause);
	}
}