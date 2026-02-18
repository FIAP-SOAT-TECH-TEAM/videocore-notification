package com.soat.fiap.videocore.notification.infrastructure.common.config.azure.svcbus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;

/**
 * Configuração do Azure Service Bus.
 * <p>
 * Define beans para envio de mensagens e mantém nomes de filas/tópicos usados
 * pelo microsserviço.
 * </p>
 */
@Configuration
public class ServiceBusConfig {

	@Value("${azure.service-bus.connection-string}")
	private String connectionString;

	/**
	 * Fila principal para processamentos de erro no processamento de um video.
	 * <p>
	 * Responsável pelo fluxo de erros no processamento do vídeo.
	 * </p>
	 */
	public static final String PROCESS_ERROR_QUEUE = "process.error.queue";

	/**
	 * Tópico de status do processamento.
	 * <p>
	 * Publica atualizações de estado dos processos.
	 * </p>
	 */
	public static final String PROCESS_STATUS_TOPIC = "process.status.topic";

	/**
	 * Assinatura do Tópico de status do processamento.
	 * <p>
	 * Microsserviço de notification.
	 * </p>
	 */
	public static final String NOTIFICATION_PROCESS_STATUS_TOPIC_SUBSCRIPTION = "notification.process.status.topic.subscription";

	/**
	 * Cria e configura beans do Service Bus para envio de mensagens.
	 */
	@Bean
	public ServiceBusClientBuilder serviceBusClientBuilder() {
		return new ServiceBusClientBuilder().connectionString(connectionString);
	}
}
