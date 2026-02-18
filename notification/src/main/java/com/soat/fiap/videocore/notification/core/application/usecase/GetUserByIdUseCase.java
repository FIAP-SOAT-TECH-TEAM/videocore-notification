package com.soat.fiap.videocore.notification.core.application.usecase;

import org.springframework.stereotype.Component;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.core.domain.exceptions.UserException;
import com.soat.fiap.videocore.notification.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;
import com.soat.fiap.videocore.notification.core.interfaceadapters.gateway.UserGateway;

import lombok.RequiredArgsConstructor;

/**
 * Caso de uso responsável por buscar um usuário a partir do seu identificador.
 */
@Component @RequiredArgsConstructor
public class GetUserByIdUseCase {

	private final UserGateway userGateway;

	/**
	 * Busca um usuário pelo seu identificador único.
	 *
	 * @param id
	 *            identificador do usuário
	 * @return {@link UserDTO} com as informações do usuário
	 */
	@WithSpan(name = "usecase.get.user.by.id")
	public UserDTO getUserById(String id) {
		if (id == null || id.isBlank())
			throw new UserException("O ID do usuário não pode ser nulo ou vazio para consulta");

		var user = userGateway.getUserById(id);

		if (user == null)
			throw new UserNotFoundException("Usuário não encontrado com o ID: %s", id);

		return user;
	}
}
