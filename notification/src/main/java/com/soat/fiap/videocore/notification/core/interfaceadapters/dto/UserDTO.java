package com.soat.fiap.videocore.notification.core.interfaceadapters.dto;

/**
 * DTO utilizado para representar dados de um usuário do sistema.
 *
 * @param subject identificador único do usuário
 * @param name    nome do usuário
 * @param email   endereço de e-mail do usuário
 */
public record UserDTO(
        String subject,
        String name,
        String email
) {
}