package ru.yandex.practicum.filmorate.validators.exeption;

public class NoFoundElementException extends RuntimeException {

    public NoFoundElementException(final String message) {
        super(message);
    }
}
