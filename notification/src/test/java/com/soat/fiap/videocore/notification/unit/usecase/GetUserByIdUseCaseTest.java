package com.soat.fiap.videocore.notification.unit.usecase;

import com.soat.fiap.videocore.notification.core.application.usecase.GetUserByIdUseCase;
import com.soat.fiap.videocore.notification.core.domain.exceptions.UserException;
import com.soat.fiap.videocore.notification.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;
import com.soat.fiap.videocore.notification.core.interfaceadapters.gateway.UserGateway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários do caso de uso {@link GetUserByIdUseCase}.
 */
class GetUserByIdUseCaseTest {

    private final UserGateway userGateway = mock(UserGateway.class);
    private final GetUserByIdUseCase useCase = new GetUserByIdUseCase(userGateway);

    @Test
    void shouldReturnUserWhenIdIsValidAndUserExists() {
        // Arrange
        var userId = "user-1";
        var expectedUser = new UserDTO(userId, "João", "joao@email.com");
        when(userGateway.getUserById(userId)).thenReturn(expectedUser);

        // Act
        var result = useCase.getUserById(userId);

        // Assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        // Arrange & Act
        var ex = assertThrows(UserException.class,
                () -> useCase.getUserById(null));

        // Assert
        assertEquals("O ID do usuário não pode ser nulo ou vazio para consulta", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIdIsBlank() {
        // Arrange & Act
        var ex = assertThrows(UserException.class,
                () -> useCase.getUserById(" "));

        // Assert
        assertEquals("O ID do usuário não pode ser nulo ou vazio para consulta", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        // Arrange
        var userId = "user-404";
        when(userGateway.getUserById(userId)).thenReturn(null);

        // Act
        var ex = assertThrows(UserNotFoundException.class,
                () -> useCase.getUserById(userId));

        // Assert
        assertTrue(ex.getMessage().contains(userId));
    }
}