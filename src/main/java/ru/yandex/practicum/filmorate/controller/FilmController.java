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

    @GetMapping("/film")
    public List<Film> findAll() {
        log.info("Take all films");
        return new ArrayList<>(filmTracker.values());
    }

    @PostMapping(value = "/film/create")
    public Film create(@Valid @RequestBody Film film) {
        if (filmValidator.validate(film)) {
            log.debug("Create new object in filmTracker " + film);
            filmTracker.put(film.getId(), film);
        } else {
            log.error("Validation error when create new object in filmTracker " + film);
            throw new ValidationException("Validation error!");
        }
        return film;
    }

    @PostMapping(value = "/film/update")
    public Film update(@Valid @RequestBody Film film) {
        if (filmValidator.validate(film)) {
            log.debug("Update object in filmTracker " + film);
            Film filmCurrent = new Film(film.getDescription());

            filmCurrent.setId(filmTracker.size() + 1);
            filmCurrent.setName(film.getName());
            filmCurrent.setReleaseDate(film.getReleaseDate());
            filmCurrent.setDuration(film.getDuration());
            filmTracker.put(filmCurrent.getId(), filmCurrent);
        } else {
            log.error("Validation error when update object in filmTracker " + film);
            throw new ValidationException("Validation error!");
        }
        return film;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotFoundFilm(final NoSuchElementException e) {
        log.error("film not found!");
        return Map.of("error", "film not found!");
    }
}
