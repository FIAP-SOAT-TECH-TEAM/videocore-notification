package com.soat.fiap.videocore.notification;

import com.azure.spring.messaging.implementation.annotation.EnableAzureMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication @Slf4j @EnableAzureMessaging
public class NotificationApplication {

    static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
	}

}
