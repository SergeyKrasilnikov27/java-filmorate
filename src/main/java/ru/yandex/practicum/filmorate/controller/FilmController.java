package ru.yandex.practicum.filmorate.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.validators.exeption.ValidationException;

import javax.validation.Valid;
import java.util.*;

@RestController
public class FilmController {

    private static HashMap<Integer, Film> filmTracker;
    private static final Logger log = Logger.getLogger(FilmController.class);
    private static final FilmValidator filmValidator = new FilmValidator();

    public FilmController() {
        this.filmTracker = new HashMap<>();
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        log.info("Take all films");
        return new ArrayList<>(filmTracker.values());
    }

    @PostMapping(value = "/films/create")
    public Film create(@Valid @RequestBody Film film) {
        if (filmValidator.validate(film)) {
            log.debug("Create new object in filmTracker " + film);
            filmTracker.put(film.getId(), film);
        }

        return film;
    }

    @PostMapping(value = "/films/update")
    public Film update(@Valid @RequestBody Film film) {
        if (!filmTracker.containsKey(film.getId())) {
            throw new NoSuchElementException("film not found!");
        }

        if (filmValidator.validate(film)) {
            log.debug("Update object in filmTracker " + film);
            Film filmCurrent = new Film(film.getDescription(), film.getReleaseDate());

            filmCurrent.setId(film.getId());
            filmCurrent.setName(film.getName());
            filmCurrent.setDuration(film.getDuration());
            filmTracker.put(filmCurrent.getId(), filmCurrent);
        }

        return film;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationError(final ValidationException e) {
        log.error("Validation error when update object in filmTracker");
        return Map.of("error", "Validation error!");
    }
}
