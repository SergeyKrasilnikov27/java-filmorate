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
@RequestMapping("/users")
public class UsersController {

    private static HashMap<Integer, User> usersTracker;
    private static final Logger log = Logger.getLogger(UsersController.class);
    private static final UserValidator userValidator = new UserValidator();
    private int usersTrackerCounter;

    public UsersController() {
        this.usersTracker = new HashMap<>();
    }

    @GetMapping
    public List<User> findAll() {
        log.info("Take all users");
        return new ArrayList(usersTracker.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (userValidator.validate(user)) {
            log.debug("Create new object in userTracker " + user);
            if (user.getId() == 0) {
                user.setId(++usersTrackerCounter);
            }
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            usersTracker.put(user.getId(), user);
        } else {
            log.error("Validation error when update object in userTracker!");
            throw new ValidationException("Validation error!");
        }

        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (!usersTracker.containsKey(user.getId())) {
            throw new NoSuchElementException("user not found!");
        }

        if (userValidator.validate(user)) {
            log.debug("Update object in userTracker " + user);

            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            } else {
                user.setName(user.getName());
            }
            usersTracker.put(user.getId(), user);
        } else {
            log.error("Validation error when update object in userTracker!");
            throw new ValidationException("Validation error!");
        }

        return user;
    }
}
