package ru.yandex.practicum.filmorate.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import ru.yandex.practicum.filmorate.validators.exeption.ValidationException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    private static HashMap<Integer, User> userTracker;
    private static final Logger log = Logger.getLogger(UserController.class);
    private static final UserValidator userValidator = new UserValidator();

    public UserController() {
        this.userTracker = new HashMap<>();
    }

    @GetMapping("/user/findAll")
    public List<User> findAll() {
        log.info("Take all users");
        return new ArrayList<>(userTracker.values());
    }

    @PostMapping(value = "/user/create")
    public User create(@Valid @RequestBody User user) {
        if (userValidator.validate(user)) {
            log.debug("Create new object in userTracker " + user.toString());
            userTracker.put(user.getId(), user);
        } else {
            log.error("Validation error when create new object in userTracker " + user.toString());
            throw new ValidationException("Validation error!");
        }
        return user;
    }

    @PostMapping(value = "/user/update")
    public User update(@Valid @RequestBody User user) {
        if (userValidator.validate(user)) {
            log.debug("Update object in userTracker " + user.toString());
            User userCurrent = new User(user.getLogin());

            userCurrent.setId(user.getId());
            userCurrent.setEmail(user.getEmail());
            userCurrent.setName(user.getName());
            userCurrent.setBirthday(user.getBirthday());
            userTracker.put(userCurrent.getId(), userCurrent);
        } else {
            log.error("Validation error when update object in userTracker " + user.toString());
            throw new ValidationException("Validation error!");
        }
        return user;
    }
}
