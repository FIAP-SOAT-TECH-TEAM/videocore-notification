package com.soat.fiap.videocore.notification.common.hints.jackson;

import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoErrorPayload;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoStatusUpdatePayload;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

/**
 * RuntimeHints para suporte a desserialização com Jackson
 * no módulo notification em ambiente GraalVM Native Image.
 *
 * <p>
 * Registra reflexão necessária para:
 * <ul>
 *     <li>Payloads recebidos via Azure Service Bus</li>
 *     <li>Tipos do Service Bus utilizados no listener</li>
 * </ul>
 *
 * <p>
 * Necessário pois o Jackson utiliza reflexão para instanciar
 * objetos durante a desserialização em runtime nativo.
 */
public class JacksonHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
        registerJacksonType(hints, ProcessVideoStatusUpdatePayload.class);
        registerJacksonType(hints, ProcessVideoErrorPayload.class);
        registerJacksonType(hints, ServiceBusReceivedMessageContext.class);
        registerJacksonType(hints, ObjectMapper.class);
    }

    private void registerJacksonType(RuntimeHints hints, Class<?> type) {
        hints.reflection().registerType(
                type,
                MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS,
                MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                MemberCategory.INVOKE_PUBLIC_METHODS,
                MemberCategory.INVOKE_DECLARED_METHODS,
                MemberCategory.ACCESS_DECLARED_FIELDS
        );
    }
}