package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

public interface FilmStorage {
    public Film createFilm(Film film);
    public Film removeFilm(Film film);
    public Film updateFilm(Film film);
    public Map<Integer, Film> getAllFilms();
}
