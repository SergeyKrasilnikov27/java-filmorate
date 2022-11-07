package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmsController {

    private final FilmService filmService;

    @Autowired
    public FilmsController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilm() {
        return new ArrayList(filmService.getAllFilm().values());
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        filmService.createFilm(film);

        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        filmService.updateFilm(film);

        return film;
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable("id") Integer id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        filmService.addLikeToFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLikeFromFilm(@PathVariable("id") Integer id, @PathVariable("userId") Integer userId) {
        filmService.removeLikeFromFilm(id, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getTopFilm(
            @RequestParam(value = "count", defaultValue = "0", required = false) Integer count) {
        return filmService.getMostPopularFilm(count);
    }
}
