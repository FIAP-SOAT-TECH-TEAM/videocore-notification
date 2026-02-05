package com.soat.fiap.videocore.notification.infrastructure.common.source;

/**
 * Fornece informações do usuário autenticado no sistema.
 * <p>
 * Esta interface expõe os dados principais que identificam e caracterizam o
 * usuário logado, de acordo com os atributos obtidos via Amazon Cognito ou
 * outro provedor de identidade configurado.
 * </p>
 */
public interface AuthenticatedUserSource {
	/**
	 * Retorna o identificador único do usuário.
	 *
	 * @return o identificador único do usuário
	 */
	String getSubject();

	/**
	 * Retorna o nome completo do usuário.
	 *
	 * @return o nome completo do usuário
	 */
	String getName();

	/**
	 * Retorna o email do usuário.
	 *
	 * @return o email do usuário
	 */
	String getEmail();
}
