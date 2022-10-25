package ru.yandex.practicum.filmorate.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.validators.exeption.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class FilmController {

    private static HashMap<Integer, Film> filmTracker;
    private static final Logger log = Logger.getLogger(FilmController.class);
    private static final FilmValidator filmValidator = new FilmValidator();

    public FilmController() {
        this.filmTracker = new HashMap<>();
    }

    @GetMapping("/film/findAll")
    public List<Film> findAll() {
        log.info("Take all films");
        return new ArrayList<>(filmTracker.values());
    }

    @PostMapping(value = "/film/create")
    public Film create(@Valid @RequestBody Film film) {
        if (filmValidator.validate(film)) {
            log.debug("Create new object in filmTracker " + film.toString());
            filmTracker.put(film.getId(), film);
        } else {
            log.error("Validation error when create new object in filmTracker " + film.toString());
            throw new ValidationException("Validation error!");
        }
        return film;
    }

    @PostMapping(value = "/film/update")
    public Film update(@Valid @RequestBody Film film) {
        if (filmValidator.validate(film)) {
            log.debug("Update object in filmTracker " + film.toString());
            Film filmCurrent = new Film(film.getName());

            filmCurrent.setId(film.getId());
            filmCurrent.setDescription(film.getDescription());
            filmCurrent.setReleaseDate(film.getReleaseDate());
            filmCurrent.setDuration(film.getDuration());
            filmTracker.put(filmCurrent.getId(), filmCurrent);
        } else {
            log.error("Validation error when update object in filmTracker " + film.toString());
            throw new ValidationException("Validation error!");
        }
        return film;
    }
}
