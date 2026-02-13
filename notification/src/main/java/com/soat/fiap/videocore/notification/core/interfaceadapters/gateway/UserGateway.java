package com.soat.fiap.videocore.notification.core.interfaceadapters.gateway;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;
import com.soat.fiap.videocore.notification.infrastructure.common.source.UserSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Gateway para operações relacionadas a usuário, delegando para {@link UserSource}.
 * Atua como intermediário entre a camada de aplicação e a fonte de dados.
 */
@Component
@RequiredArgsConstructor
public class UserGateway {

    private final UserSource userSource;

    /**
     * Recupera um usuário a partir de seu identificador único.
     *
     * @param id identificador do usuário
     * @return {@link UserDTO} com as informações do usuário
     */
    @WithSpan(name = "gateway.get.user.by.id")
    public UserDTO getUserById(String id) {
        return userSource.getUserById(id);
    }
}