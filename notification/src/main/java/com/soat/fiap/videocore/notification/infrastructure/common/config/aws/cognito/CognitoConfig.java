package com.soat.fiap.videocore.notification.infrastructure.common.config.aws.cognito;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Configura o cliente Cognito apenas se 'aws.cognito.userPoolId' estiver
 * definido. Usa credenciais do formato AWS Academy na variável única
 * 'AWS_CREDENTIALS'.
 */
@Configuration
@ConditionalOnProperty(prefix = "aws.cognito", name = "userPoolId")
public class CognitoConfig {

	@Value("${aws.cognito.endpoint}")
	private String endpoint;

	@Value("${aws.cognito.aws-credentials}")
	private String creds;

	@Bean
	public CognitoIdentityProviderClient cognitoClient() {
		if (creds == null || creds.isBlank()) {
			throw new IllegalStateException("AWS_CREDENTIALS não definido");
		}

		var parsed = parseCredentials(creds);
		var sessionCreds = AwsSessionCredentials.create(parsed.get("AWS_ACCESS_KEY_ID"),
				parsed.get("AWS_SECRET_ACCESS_KEY"), parsed.get("AWS_SESSION_TOKEN"));

		var builder = CognitoIdentityProviderClient.builder()
				.region(Region.of(parsed.getOrDefault("AWS_REGION", "us-east-1")))
				.credentialsProvider(StaticCredentialsProvider.create(sessionCreds));

		if (endpoint != null && !endpoint.isBlank()) {
			builder.endpointOverride(URI.create(endpoint));
		}

		return builder.build();
	}

    /**
     * Converte uma string de credenciais no formato "chave=valor;chave=valor" em um mapa.
     *
     * @param creds string contendo as credenciais separadas por ponto e vírgula
     * @return mapa com chaves e valores extraídos da string
     */
	private Map<String, String> parseCredentials(String creds) {
		Map<String, String> map = new HashMap<>();
		for (String pair : creds.split(";")) {
			String[] kv = pair.split("=", 2);
			if (kv.length == 2)
				map.put(kv[0].trim(), kv[1].trim());
		}
		return map;
	}
}
