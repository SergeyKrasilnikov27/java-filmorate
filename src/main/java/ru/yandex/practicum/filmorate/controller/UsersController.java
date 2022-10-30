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
public class UsersController {

    private static HashMap<Integer, User> usersTracker;
    private static final Logger log = Logger.getLogger(UsersController.class);
    private static final UserValidator userValidator = new UserValidator();
    private int usersTrackerCounter;

    public UsersController() {
        this.usersTracker = new HashMap<>();
    }

    @GetMapping("/users")
    public List<User> findAll() {
        log.info("Take all users");
        return new ArrayList<>(usersTracker.values());
    }

    @PostMapping(value = "/users/create")
    public User create(@Valid @RequestBody User user) {
        if (userValidator.validate(user)) {
            log.debug("Create new object in userTracker " + user);
            if (user.getId() == 0) {
                user.setId(++usersTrackerCounter);
            }
            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            }
            usersTracker.put(user.getId(), user);
        } else {
            log.error("Validation error when update object in userTracker!");
            throw new ValidationException("Validation error!");
        }

        return user;
    }

    @PutMapping(value = "/users/update")
    public User update(@Valid @RequestBody User user) {
        if (!usersTracker.containsKey(user.getId())) {
            throw new NoSuchElementException("user not found!");
        }

        if (userValidator.validate(user)) {
            log.debug("Update object in userTracker " + user);
            User userCurrent = new User();

            userCurrent.setId(user.getId());
            userCurrent.setEmail(user.getEmail());
            userCurrent.setLogin(user.getLogin());
            if (user.getName().isEmpty()) {
                userCurrent.setName(user.getLogin());
            } else {
                userCurrent.setName(user.getName());
            }
            userCurrent.setBirthday(user.getBirthday());
            usersTracker.put(userCurrent.getId(), userCurrent);
        } else {
            log.error("Validation error when update object in userTracker!");
            throw new ValidationException("Validation error!");
        }

        return user;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationError(final ValidationException e) {
        log.error("Validation error when update object in userTracker!");
        return Map.of("error", "Validation error!");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotFoundUser(final NoSuchElementException e) {
        log.error("User not found!");
        return Map.of("error", "user not found!");
    }
}
