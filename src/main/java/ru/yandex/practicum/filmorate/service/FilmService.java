package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.validators.exeption.NoFoundElementException;
import ru.yandex.practicum.filmorate.validators.exeption.ValidationException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;
    private final FilmValidator filmValidator;

    @Autowired
    public FilmService(FilmStorage filmStorage, FilmValidator filmValidator, UserService userService) {
        this.filmStorage = filmStorage;
        this.filmValidator = filmValidator;
        this.userService = userService;
    }

    public Film createFilm(Film film) {
        if (filmValidator.validate(film)) {
            log.info("Create new object in filmStorage with id = " + film.getId());
            filmStorage.createFilm(film);
        } else {
            log.error("Validation error when update object in FilmStorage!");
            throw new ValidationException("Validation error!");
        }

        return film;
    }

    public Film removeFilm(int idFilm) {
        Film film = filmStorage.gitFilmById(idFilm);
        if (filmValidator.validate(film)) {
            log.info("Create new object in filmStorage with id = " + film.getId());
            filmStorage.removeFilm(film);
        } else {
            log.error("Validation error when update object in FilmStorage!");
            throw new ValidationException("Validation error!");
        }

        return film;
    }

    public void updateFilm(Film film) {
        filmStorage.checkAvailabilityOfFilm(film.getId());

        if (filmValidator.validate(film)) {
            log.info("Update object in filmTracker " + film);

            filmStorage.updateFilm(film);
        } else {
            log.error("Validation error when update object in FilmStorage!");
            throw new NoFoundElementException("Validation error!");
        }
    }

    public void addLikeToFilm(int id, int idUser) {
        if (!userService.getUsersTracker().containsKey(idUser)) {
            log.error("addLikeToFilm : User with id = " + idUser + " not found!");
            throw new NoFoundElementException("User with id = " + idUser + " not found!");
        }

        log.info("Add like to film with id = " + id);
        addLike(id, idUser);
    }

    public void addLike(int id, int idUser) {
        getFilmById(id).addLike(idUser);
    }

    public void removeLikeFromFilm(int id, int idUser) {
        if (!userService.getUsersTracker().containsKey(idUser)) {
            log.error("removeLikeFromFilm : User with id = " + idUser + " not found!");
            throw new NoFoundElementException("User with id = " + idUser + " not found!");
        }

        log.info("Remove like from film by id = " + id);
        getFilmById(id).removeLike(idUser);
    }

    public List<Film> getMostPopularFilm(int count) {
        log.info("Remove most popular film");
        return filmStorage
                .getAllFilms()
                .stream()
                .sorted(Comparator.comparingInt(film -> film.getLikes().size() * (-1)))
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<Film> getAllFilm() {
        log.info("Take all films");
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(int id) {
        if (!filmStorage.getFilmsTracker().containsKey(id)) {
            log.error("Film not found! id = " + id);
            throw new NoFoundElementException("Film not found! id = " + id);
        }

        log.info("Get film by id = " + id);
        return filmStorage.gitFilmById(id);
    }
}
