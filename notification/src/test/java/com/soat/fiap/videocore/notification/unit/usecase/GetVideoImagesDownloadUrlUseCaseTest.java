package com.soat.fiap.videocore.notification.unit.usecase;

import com.soat.fiap.videocore.notification.core.application.usecase.GetVideoImagesDownloadUrlUseCase;
import com.soat.fiap.videocore.notification.core.domain.exceptions.VideoException;
import com.soat.fiap.videocore.notification.core.domain.exceptions.VideoImageDownloadUrlNotFoundException;
import com.soat.fiap.videocore.notification.core.interfaceadapters.gateway.VideoGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários do caso de uso {@link GetVideoImagesDownloadUrlUseCase}.
 */
class GetVideoImagesDownloadUrlUseCaseTest {

    private final VideoGateway videoGateway = mock(VideoGateway.class);
    private final GetVideoImagesDownloadUrlUseCase useCase =
            new GetVideoImagesDownloadUrlUseCase(videoGateway);

    @Test
    void shouldReturnDownloadUrlWhenInputsAreValid() {
        // Arrange
        var userId = "user-1";
        var requestId = "req-1";
        var videoName = "video.mp4";
        var expectedUrl = "http://download/url";

        when(videoGateway.getVideoImagesDownloadUrl(
                userId, requestId, videoName, 30L)
        ).thenReturn(expectedUrl);

        // Act
        var result = useCase.getVideoImagesDownloadUrl(userId, requestId, videoName);

        // Assert
        assertEquals(expectedUrl, result);
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsNull() {
        // Arrange & Act
        var ex = assertThrows(VideoException.class,
                () -> useCase.getVideoImagesDownloadUrl(null, "req", "video"));

        // Assert
        assertEquals(
                "userId não pode ser nulo ou vazio para pesquisa de URL de download das imagens",
                ex.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenRequestIdIsBlank() {
        // Arrange & Act
        var ex = assertThrows(VideoException.class,
                () -> useCase.getVideoImagesDownloadUrl("user", " ", "video"));

        // Assert
        assertEquals(
                "requestId não pode ser nulo ou vazio para pesquisa de URL de download das imagens",
                ex.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenVideoNameIsNull() {
        // Arrange & Act
        var ex = assertThrows(VideoException.class,
                () -> useCase.getVideoImagesDownloadUrl("user", "req", null));

        // Assert
        assertEquals(
                "videoName não pode ser nulo ou vazio para pesquisa de URL de download das imagens",
                ex.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenDownloadUrlIsNotFound() {
        // Arrange
        var userId = "user-1";
        var requestId = "req-1";
        var videoName = "video.mp4";

        when(videoGateway.getVideoImagesDownloadUrl(
                userId, requestId, videoName, 30L)
        ).thenReturn(" ");

        // Act
        var ex = assertThrows(VideoImageDownloadUrlNotFoundException.class,
                () -> useCase.getVideoImagesDownloadUrl(userId, requestId, videoName));

        // Assert
        assertTrue(ex.getMessage().contains(userId));
        assertTrue(ex.getMessage().contains(requestId));
        assertTrue(ex.getMessage().contains(videoName));
    }
}