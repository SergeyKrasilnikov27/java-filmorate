package ru.yandex.practicum.filmorate.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import ru.yandex.practicum.filmorate.validators.exeption.ValidationException;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {

    private static HashMap<Integer, User> userTracker;
    private static final Logger log = Logger.getLogger(UserController.class);
    private static final UserValidator userValidator = new UserValidator();

    public UserController() {
        this.userTracker = new HashMap<>();
    }

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("Take all users");
        return new ArrayList<>(userTracker.values());
    }

    @PostMapping(value = "/users/create")
    public User create(@Valid @RequestBody User user) {
        if (userValidator.validate(user)) {
            log.debug("Create new object in userTracker " + user);
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            userTracker.put(user.getId(), user);
        }

        return user;
    }

    @PostMapping(value = "/users/update")
    public User update(@Valid @RequestBody User user) {
        if (!userTracker.containsKey(user.getId())) {
            throw new NoSuchElementException("user not found!");
        }

        if (userValidator.validate(user)) {
            log.debug("Update object in userTracker " + user);
            User userCurrent = new User(user.getEmail(), user.getLogin());

            userCurrent.setId(user.getId());
            if (user.getName().isEmpty()) {
                userCurrent.setName(user.getLogin());
            } else {
                userCurrent.setName(user.getName());
            }
            userCurrent.setBirthday(user.getBirthday());
            userTracker.put(userCurrent.getId(), userCurrent);
        }

        return user;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotFoundUser(final NoSuchElementException e) {
        log.error("User not found!");
        return Map.of("error", "user not found!");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationError(final ValidationException e) {
        log.error("Validation error when update object in userTracker!");
        return Map.of("error", "Validation error!");
    }
}
