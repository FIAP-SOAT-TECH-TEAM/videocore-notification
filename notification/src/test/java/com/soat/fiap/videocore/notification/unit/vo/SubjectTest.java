package com.soat.fiap.videocore.notification.unit.vo;

import com.soat.fiap.videocore.notification.core.domain.exceptions.NotificationException;
import com.soat.fiap.videocore.notification.core.domain.vo.Subject;
import com.soat.fiap.videocore.notification.fixture.NotificationFixture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do Value Object {@link Subject}.
 */
class SubjectTest {

    @Test
    void shouldCreateValidSubject() {
        // arrange
        var subject = NotificationFixture.validSubject();

        // act
        var value = subject.value();

        // assert
        assertEquals("Assunto válido", value);
    }

    @Test
    void shouldThrowExceptionWhenSubjectIsNull() {
        // arrange / act
        var ex = assertThrows(NotificationException.class, () -> new Subject(null));

        // assert
        assertEquals("O assunto da notificação não pode ser nulo ou vazio", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSubjectIsBlank() {
        // arrange / act
        var ex = assertThrows(NotificationException.class, () -> new Subject(" "));

        // assert
        assertEquals("O assunto da notificação não pode ser nulo ou vazio", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSubjectExceedsMaxLength() {
        // arrange
        var value = "a".repeat(101);

        // act
        var ex = assertThrows(NotificationException.class, () -> new Subject(value));

        // assert
        assertEquals("O assunto da notificação não pode exceder: 100 caracteres", ex.getMessage());
    }
}