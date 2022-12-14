package ru.yandex.practicum.filmorate.validators;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FilmValidator {

    final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final static LocalDate lastFilmDate = LocalDate.parse("1895-12-28", formatter);

    public boolean validate(Film film) {
        boolean approveFilm = true;

        if (film.getName() == null || film.getName().isEmpty()) {
            approveFilm = false;
        } else if (film.getDescription().isEmpty()) {
            approveFilm = false;
        } else if (film.getDescription().length() > 200) {
            approveFilm = false;
        } else if (film.getDuration() < 0) {
            approveFilm = false;
        } else if (lastFilmDate.isAfter(film.getReleaseDate())) {
            approveFilm = false;
        }

        return approveFilm;
    }
}
