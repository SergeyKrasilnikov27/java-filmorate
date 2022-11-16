package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.exeption.NoFoundElementException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage  implements FilmStorage {
    private static HashMap<Integer, Film> filmsTracker;
    private int filmsTrackerCounter;

    public InMemoryFilmStorage() {
        this.filmsTracker = new HashMap<>();
    }

    @Override
    public Film createFilm(Film film) {
        log.debug("Create new object in filmTracker " + film);
        if (film.getId() == 0) {
            film.setId(addCounter());
        }
        filmsTracker.put(film.getId(), film);

        return film;
    }

    @Override
    public Film removeFilm(Film film) {
        if (!filmsTracker.containsKey(film.getId()) || film.getId() == 0) {
            throw new NoFoundElementException("film not found!");
        }

        filmsTracker.remove(film.getId());
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!filmsTracker.containsKey(film.getId()) || film.getId() == 0) {
            throw new NoFoundElementException("film not found!");
        }

        log.debug("Update object in filmTracker " + film);
        filmsTracker.put(film.getId(), film);

        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(filmsTracker.values());
    }

    private int addCounter(){
        return ++filmsTrackerCounter;
    }

    @Override
    public void checkAvailabilityOfFilm(int idFilm) {
        if (!filmsTracker.containsKey(idFilm)) {
            log.error("Film not found! id = " + idFilm);
            throw new NoFoundElementException("Film not found! id = " + idFilm);
        }
    }

    @Override
    public Film gitFilmById(int id) {
        checkAvailabilityOfFilm(id);

        return filmsTracker.get(id);
    }

    @Override
    public Map<Integer, Film> getFilmsTracker() {
        return filmsTracker;
    }
}
