package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmsController;
import ru.yandex.practicum.filmorate.controller.UsersController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
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

    @Test
    void test() {
        FilmsController filmController = new FilmsController(new FilmService(new InMemoryFilmStorage(), new FilmValidator(), new UserService(new InMemoryUserStorage(), new UserValidator())));
        UsersController userController = new UsersController(new UserService(new InMemoryUserStorage(), new UserValidator()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayDate = LocalDate.parse("1946-08-20", formatter);

        User user = new User();
        user.setBirthday(birthdayDate);
        user.setLogin("common");
        user.setEmail("mail@mail.ru");
        user.setName("Nick Name");

        userController.createUser(user);

        LocalDate filmDate = LocalDate.parse("1999-04-30", formatter);
        Film film = new Film();
        film.setDuration(190);
        film.setName("New film");
        film.setReleaseDate(filmDate);
        film.setDescription("New film about friends");
        film.setRate(4);

        filmController.createFilm(film);

        LocalDate filmDate1 = LocalDate.parse("1999-04-30", formatter);
        Film film1 = new Film();
        film1.setDuration(190);
        film1.setName("New film");
        film1.setReleaseDate(filmDate1);
        film1.setDescription("New film friends");
        film1.setRate(4);

        filmController.createFilm(film1);

        filmController.addLikeToFilm(2, 1);
        filmController.removeLikeFromFilm(2, 1);
    }

    /*@Test
    void test2() {
        FilmsController filmController = new FilmsController(new FilmService(new InMemoryFilmStorage(), new FilmValidator(), new UserService(new InMemoryUserStorage(), new UserValidator())));
        UsersController userController = new UsersController(new UserService(new InMemoryUserStorage(), new UserValidator()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdayDate = LocalDate.parse("1946-08-20", formatter);
        LocalDate birthdayDate1 = LocalDate.parse("1976-09-20", formatter);

        User user = new User();
        user.setBirthday(birthdayDate);
        user.setLogin("dolore");
        user.setEmail("mail@mail.ru");
        user.setName("Nick Name");

        User user1 = new User();
        user1.setBirthday(birthdayDate1);
        user1.setLogin("doloreUpdate");
        user1.setEmail("mail@yandex.ru");
        user1.setName("est adipisicing");

        userController.createUser(user);
        userController.createUser(user1);
        userController.addFriend(user.getId(), user1.getId());
        userController.getUserFriends(user.getId());
    }*/
}
