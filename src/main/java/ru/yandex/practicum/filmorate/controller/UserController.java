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

    @GetMapping("/user")
    public List<User> findAll() {
        log.info("Take all users");
        return new ArrayList<>(userTracker.values());
    }

    @PostMapping(value = "/user/create")
    public User create(@Valid @RequestBody User user) {
        if (userValidator.validate(user)) {
            log.debug("Create new object in userTracker " + user);
            userTracker.put(user.getId(), user);
        } else {
            log.error("Validation error when create new object in userTracker " + user);
            throw new ValidationException("Validation error!");
        }
        return user;
    }

    @PostMapping(value = "/user/update")
    public User update(@Valid @RequestBody User user) {
        if (userValidator.validate(user)) {
            log.debug("Update object in userTracker " + user);
            User userCurrent = new User(user.getEmail(), user.getLogin());

            userCurrent.setId(userTracker.size() + 1);
            if (user.getName().isEmpty()) {
                userCurrent.setName(user.getLogin());
            } else {
                userCurrent.setName(user.getName());
            }
            userCurrent.setBirthday(user.getBirthday());
            userTracker.put(userCurrent.getId(), userCurrent);
        } else {
            log.error("Validation error when update object in userTracker " + user);
            throw new ValidationException("Validation error!");
        }
        return user;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleNotFoundUser(final NoSuchElementException e) {
        log.error("user not found!");
        return Map.of("error", "user not found!");
    }
}
