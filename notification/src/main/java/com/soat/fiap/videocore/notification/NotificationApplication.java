package com.soat.fiap.videocore.notification;

import com.soat.fiap.videocore.notification.common.hints.azure.BlobHints;
import com.soat.fiap.videocore.notification.common.hints.jackson.JacksonHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints({BlobHints.class, JacksonHints.class})
public class NotificationApplication {

    static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
	}

}
