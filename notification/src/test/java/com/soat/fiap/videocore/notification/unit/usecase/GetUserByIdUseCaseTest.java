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
        // arrange
        var userId = "user-1";
        var expectedUser = new UserDTO(userId, "João", "joao@email.com");
        when(userGateway.getUserById(userId)).thenReturn(expectedUser);

        // act
        var result = useCase.getUserById(userId);

        // assert
        assertNotNull(result);
        assertEquals(expectedUser, result);
    }

    @Test
    void shouldThrowExceptionWhenIdIsNull() {
        // arrange / act
        var ex = assertThrows(UserException.class,
                () -> useCase.getUserById(null));

        // assert
        assertEquals("O ID do usuário não pode ser nulo ou vazio para consulta", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIdIsBlank() {
        // arrange / act
        var ex = assertThrows(UserException.class,
                () -> useCase.getUserById(" "));

        // assert
        assertEquals("O ID do usuário não pode ser nulo ou vazio para consulta", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        // arrange
        var userId = "user-404";
        when(userGateway.getUserById(userId)).thenReturn(null);

        // act
        var ex = assertThrows(UserNotFoundException.class,
                () -> useCase.getUserById(userId));

        // assert
        assertTrue(ex.getMessage().contains(userId));
    }
}