package com.soat.fiap.videocore.notification.core.interfaceadapters.gateway;

import com.soat.fiap.videocore.notification.common.observability.trace.WithSpan;
import com.soat.fiap.videocore.notification.infrastructure.common.source.AuthenticatedUserSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Gateway para acessar informações do usuário autenticado no sistema.
 * <p>
 * Cada método expõe um atributo específico do usuário obtido via
 * {@link AuthenticatedUserSource}.
 * </p>
 */
@Slf4j
@Component
public class AuthenticatedUserGateway {

	private final AuthenticatedUserSource authenticatedUserSource;

	/**
	 * Construtor do gateway.
	 *
	 * @param authenticatedUserSource
	 *            fonte de dados do usuário autenticado
	 */
	public AuthenticatedUserGateway(AuthenticatedUserSource authenticatedUserSource) {
		this.authenticatedUserSource = authenticatedUserSource;
	}

	/**
	 * Retorna o identificador único (sub) do usuário autenticado.
	 *
	 * @return sub do usuário
	 */
    @WithSpan(name = "gateway.get.auth.subject")
	public String getSubject() {
		log.debug("Obtendo sub do usuário autenticado");
		return authenticatedUserSource.getSubject();
	}

	/**
	 * Retorna o nome completo do usuário autenticado.
	 *
	 * @return nome do usuário
	 */
    @WithSpan(name = "gateway.get.auth.name")
	public String getName() {
		log.debug("Obtendo nome do usuário autenticado");
		return authenticatedUserSource.getName();
	}

	/**
	 * Retorna o email do usuário autenticado.
	 *
	 * @return email do usuário
	 */
    @WithSpan(name = "gateway.get.auth.email")
	public String getEmail() {
		log.debug("Obtendo email do usuário autenticado");
		return authenticatedUserSource.getEmail();
	}
}
