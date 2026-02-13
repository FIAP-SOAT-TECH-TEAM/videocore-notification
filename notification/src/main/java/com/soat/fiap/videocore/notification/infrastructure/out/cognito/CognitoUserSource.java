package com.soat.fiap.videocore.notification.infrastructure.out.cognito;

import com.soat.fiap.videocore.notification.core.interfaceadapters.dto.UserDTO;
import com.soat.fiap.videocore.notification.infrastructure.common.source.UserSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;
import software.amazon.awssdk.services.cognitoidentityprovider.model.ListUsersRequest;

/**
 * Implementação de {@link UserSource} que consulta usuários no Amazon Cognito.
 */
@Component
@ConditionalOnProperty(prefix = "aws.cognito", name = "userPoolId")
@RequiredArgsConstructor
public class CognitoUserSource implements UserSource {

    private final CognitoIdentityProviderClient cognitoClient;

    /**
     * Identificador do User Pool do Cognito.
     */
    @Value("${aws.cognito.userPoolId}")
    private String userPoolId;

    /**
     * Obtém um usuário do Cognito a partir do subject (sub).
     *
     * @param subject identificador único do usuário no Cognito
     * @return {@link UserDTO} preenchido ou {@code null} se não encontrado
     */
    @Override
    public UserDTO getUserById(String subject) {
        try {
            var request = ListUsersRequest.builder()
                    .userPoolId(userPoolId)
                    .filter(String.format("sub = \"%s\"", subject))
                    .limit(1)
                    .build();

            var response = cognitoClient.listUsers(request);

            if (response.users().isEmpty()) {
                return null;
            }

            var cognitoUser = response.users().getFirst();

            var userSubject = "";
            var userName = "";
            var userMail = "";

            for (var attr : cognitoUser.attributes()) {
                switch (attr.name()) {
                    case "sub" -> userSubject = attr.value();
                    case "email" -> userMail = attr.value();
                    case "name" -> userName = attr.value();
                }
            }

            return new UserDTO(userSubject, userName, userMail);
        } catch (CognitoIdentityProviderException e) {
            throw new IllegalStateException("Erro ao consultar usuário no Cognito", e);
        }
    }
}