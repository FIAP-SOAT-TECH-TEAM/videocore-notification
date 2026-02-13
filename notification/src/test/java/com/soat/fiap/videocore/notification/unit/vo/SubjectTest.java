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
        // Arrange
        var subject = NotificationFixture.validSubject();

        // Act
        var value = subject.value();

        // Assert
        assertEquals("Assunto válido", value);
    }

    @Test
    void shouldThrowExceptionWhenSubjectIsNull() {
        // Arrange & Act
        var ex = assertThrows(NotificationException.class, () -> new Subject(null));

        // Assert
        assertEquals("O assunto da notificação não pode ser nulo ou vazio", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSubjectIsBlank() {
        // Arrange & Act
        var ex = assertThrows(NotificationException.class, () -> new Subject(" "));

        // Assert
        assertEquals("O assunto da notificação não pode ser nulo ou vazio", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenSubjectExceedsMaxLength() {
        // Arrange
        var value = "a".repeat(101);

        // Act
        var ex = assertThrows(NotificationException.class, () -> new Subject(value));

        // Assert
        assertEquals("O assunto da notificação não pode exceder: 100 caracteres", ex.getMessage());
    }
}