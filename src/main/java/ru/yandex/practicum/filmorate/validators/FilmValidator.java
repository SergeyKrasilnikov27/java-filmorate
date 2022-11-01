package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilmValidator {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");
    final static LocalDateTime lastFilmDate = LocalDateTime.parse("1895-12-28-00-00", formatter);

    public boolean validate(Film film) {
        boolean approveFilm = true;

        if (film.getDescription().isEmpty()) {
            approveFilm = false;
        } else if (film.getDescription().length() > 200) {
            approveFilm = false;
        } else if (film.getDuration() < 0) {
            approveFilm = false;
        } else if (lastFilmDate.isAfter(film.getReleaseDate())) {
            approveFilm = false;
        } else if (film.getName().isEmpty() || film.getName() == null) {
            approveFilm = false;
        }

        return approveFilm;
    }
}
