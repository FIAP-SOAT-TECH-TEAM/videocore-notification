package com.soat.fiap.videocore.notification.infrastructure.common.config.environment;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * Propriedades relacionadas ao ambiente de execução da aplicação.
 */
@Component @RequiredArgsConstructor
public class EnvironmentProperties {

	private final Environment environment;

	/**
	 * Indica se o profile ativo é produção.
	 *
	 * @return true se o profile "prod" estiver ativo
	 */
	public boolean isProd() {
		return environment.matchesProfiles("prod");
	}
}
