package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.FilmValidator;
import ru.yandex.practicum.filmorate.validators.UserValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class JavaFilmorateApplicationTests {

    @Test
    @DisplayName("Проверка имени фильма на пустоту")
    void testGetCheckEmptyFilmName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate filmDate = LocalDate.parse("2001-12-28", formatter);

        Film film = new Film();
        film.setDuration(190);
        film.setName("");
        film.setId(1);
        film.setReleaseDate(filmDate);
        film.setDescription("something");

        FilmValidator filmValidator = new FilmValidator();
        boolean assertResult = filmValidator.validate(film);

        assertFalse(assertResult);
    }

    @Test
    @DisplayName("Проверка описания фильма")
    void testGetCheckMaxFilmDuration() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate filmDate = LocalDate.parse("2001-12-28", formatter);

        Film film = new Film();
        film.setDuration(190);
        film.setName("something");
        film.setId(1);
        film.setReleaseDate(filmDate);
        film.setDescription("somethingsomethingsomethingsomethingsomethingsomethingsomethingsomething" +
                "somethingsomethingsomethingsomethingsomethingsomethingsomethingsomethingsomethingsomething" +
                "somethingsomethingsomethingsomethingsomethingsomethingsomethingsomethingsomethingsomething");

        FilmValidator filmValidator = new FilmValidator();
        boolean assertResult = filmValidator.validate(film);

        assertFalse(assertResult);
    }

    @Test
    @DisplayName("Проверка положительного знака продолжительности фильма")
    void testGetCheckPositivelyFilmDuration() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate filmDate = LocalDate.parse("2001-12-28", formatter);

        Film film = new Film();
        film.setDuration(-2);
        film.setName("something");
        film.setId(1);
        film.setReleaseDate(filmDate);
        film.setDescription("something");

        FilmValidator filmValidator = new FilmValidator();
        boolean assertResult = filmValidator.validate(film);

        assertFalse(assertResult);
    }

    @Test
    @DisplayName("Проверка даты фильма")
    void testGetCheckDateFilm() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate filmDate = LocalDate.parse("1894-12-28", formatter);

        Film film = new Film();
        film.setDuration(190);
        film.setName("something");
        film.setId(1);
        film.setReleaseDate(filmDate);
        film.setDescription("something");

        FilmValidator filmValidator = new FilmValidator();
        boolean assertResult = filmValidator.validate(film);

        assertFalse(assertResult);
    }

    @Test
    @DisplayName("Проверка имейла пользователя на наличие знака @")
    void testGetCheckEmailUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayDate = LocalDate.parse("1994-12-28", formatter);

        User user = new User();
        user.setId(1);
        user.setBirthday(birthdayDate);
        user.setLogin("Sergey");
        user.setEmail("это-неправильный?эмейл");
        user.setName("Sergey");

        UserValidator userValidator = new UserValidator();
        boolean assertResult = userValidator.validate(user);

        assertFalse(assertResult);
    }

    @Test
    @DisplayName("Проверка пустоты логина пользователя")
    void testGetCheckLoginEmptyUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayDate = LocalDate.parse("1994-12-28", formatter);

        User user = new User();
        user.setId(1);
        user.setBirthday(birthdayDate);
        user.setLogin("");
        user.setEmail("Правильный@емейл");
        user.setName("Sergey");

        UserValidator userValidator = new UserValidator();
        boolean assertResult = userValidator.validate(user);

        assertFalse(assertResult);
    }

    @Test
    @DisplayName("Проверка пустых символов в логине пользователя")
    void testGetCheckLoginVoidPositionUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayDate = LocalDate.parse("1994-12-28", formatter);

        User user = new User();
        user.setId(1);
        user.setBirthday(birthdayDate);
        user.setLogin("Sergey ");
        user.setEmail("Правильный@емейл");
        user.setName("Sergey");

        UserValidator userValidator = new UserValidator();
        boolean assertResult = userValidator.validate(user);

        assertFalse(assertResult);
    }

    @Test
    @DisplayName("Проверка даты рождения пользователя")
    void testGetCheckBirthdayUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayDate = LocalDate.parse("2040-12-28", formatter);

        User user = new User();
        user.setId(1);
        user.setBirthday(birthdayDate);
        user.setLogin("Sergey");
        user.setEmail("Правильный@емейл");
        user.setName("Sergey");

        UserValidator userValidator = new UserValidator();
        boolean assertResult = userValidator.validate(user);

        assertFalse(assertResult);
    }

    /*@Test
    void test() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayDate = LocalDate.parse("1946-08-20", formatter);

        User user = new User();
        user.setBirthday(birthdayDate);
        user.setLogin("dolore");
        user.setEmail("mail@mail.ru");
        user.setName("Nick Name");

        UserValidator userValidator = new UserValidator();
        userValidator.validate(user);

    }*/
}
