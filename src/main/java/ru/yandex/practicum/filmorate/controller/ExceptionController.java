package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.validators.exeption.NoFoundElementException;
import ru.yandex.practicum.filmorate.validators.exeption.ValidationException;

import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationError(final ValidationException e) {
        log.error("Validation error: " + e.getMessage());
        return Map.of("error", "Validation error: " + e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundFilm(final NoFoundElementException e) {
        log.error("Object not found!");
        return Map.of("error", "Object not found: " + e.getMessage());
    }
}
