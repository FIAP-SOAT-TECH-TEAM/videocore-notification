package com.soat.fiap.videocore.notification;

import com.soat.fiap.videocore.notification.common.hints.azure.BlobHints;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoErrorPayload;
import com.soat.fiap.videocore.notification.infrastructure.in.event.azsvcbus.payload.ProcessVideoStatusUpdatePayload;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(BlobHints.class)
@RegisterReflectionForBinding({ProcessVideoStatusUpdatePayload.class, ProcessVideoErrorPayload.class})
public class NotificationApplication {

    static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
	}

}
