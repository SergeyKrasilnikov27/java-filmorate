package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film createFilm(Film film);

    Film removeFilm(Film film);

    Film updateFilm(Film film);

    List<Film> getAllFilms();

    void checkAvailabilityOfFilm(int idFilm);

    Film getFilmById(int id);
}
