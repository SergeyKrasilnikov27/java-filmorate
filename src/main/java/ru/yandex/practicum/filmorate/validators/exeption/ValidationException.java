package ru.yandex.practicum.filmorate.validators.exeption;

public class ValidationException extends RuntimeException {

    public ValidationException(final String message) {
        super(message);
    }
}
