package com.soat.fiap.videocore.notification.unit.model;

import com.soat.fiap.videocore.notification.core.domain.model.Notification;
import com.soat.fiap.videocore.notification.fixture.NotificationFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários da entidade {@link Notification}.
 */
class NotificationTest {

    @Test
    void shouldCreateValidNotification() {
        // arrange
        var notification = NotificationFixture.validNotification();

        // act / assert
        assertEquals("João Silva", notification.getRecipientName());
        assertEquals("Assunto válido", notification.getSubject());
        assertEquals("joao@email.com", notification.getRecipient());
        assertEquals("Mensagem válida", notification.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRecipientNameIsNull() {
        // arrange
        var subject = NotificationFixture.validSubject();
        var recipient = NotificationFixture.validRecipient();
        var message = NotificationFixture.validMessage();

        // act
        var ex = assertThrows(NullPointerException.class,
                () -> new Notification(null, subject, recipient, message));

        // assert
        assertEquals("recipientName não pode ser nulo", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSubjectIsNull() {
        // arrange
        var recipientName = NotificationFixture.validRecipientName();
        var recipient = NotificationFixture.validRecipient();
        var message = NotificationFixture.validMessage();

        // act
        var ex = assertThrows(NullPointerException.class,
                () -> new Notification(recipientName, null, recipient, message));

        // assert
        assertEquals("subject não pode ser nulo", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRecipientIsNull() {
        // arrange
        var recipientName = NotificationFixture.validRecipientName();
        var subject = NotificationFixture.validSubject();
        var message = NotificationFixture.validMessage();

        // act
        var ex = assertThrows(NullPointerException.class,
                () -> new Notification(recipientName, subject, null, message));

        // assert
        assertEquals("recipient não pode ser nulo", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenMessageIsNull() {
        // arrange
        var recipientName = NotificationFixture.validRecipientName();
        var subject = NotificationFixture.validSubject();
        var recipient = NotificationFixture.validRecipient();

        // act
        var ex = assertThrows(NullPointerException.class,
                () -> new Notification(recipientName, subject, recipient, null));

        // assert
        assertEquals("message não pode ser nulo", ex.getMessage());
    }
}