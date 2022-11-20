package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import ru.yandex.practicum.filmorate.validators.exeption.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private UserStorage userStorage;
    private final UserValidator userValidator;

    @Autowired
    public UserService(UserStorage userStorage, UserValidator userValidator) {
        this.userStorage = userStorage;
        this.userValidator = userValidator;
    }

    public User createUser(User user) {
        if (userValidator.validate(user)) {
            log.info("Create new object in userStorage with id = " + user.getId());
            if (user.getName() == null || user.getName().isBlank()) {
                user.setName(user.getLogin());
            }
            userStorage.createUser(user);
        } else {
            log.error("Validation error when update object in UserService!");
            throw new ValidationException("Validation error!");
        }
        return user;
    }

    public void removeFriend(int id, int friendId) {
        log.info("Remove friend to user by id = " + id);
        getUserById(id).removeFriend(friendId);
        getUserById(friendId).removeFriend(id);
    }

    public void removeUser(int id) {
        User user = getUserById(id);
        log.info("Remove user to user by id = " + id);
        userStorage.removeUser(user);
    }

    public void addFriend(int id, int friendId) {
        log.info("Add friend to user by id = " + id);
        getUserById(id).addFriend(friendId);
        getUserById(friendId).addFriend(id);
    }

    public void updateUser(User user) {
        userStorage.checkAvailabilityOfUser(user.getId());

        if (userValidator.validate(user)) {
            log.info("Update object in userTracker " + user);

            if (user.getName().isEmpty()) {
                user.setName(user.getLogin());
            } else {
                user.setName(user.getName());
            }
            userStorage.updateUser(user);
        } else {
            log.debug("Validation error when update object in userTracker!");
            throw new ValidationException("Validation error!");
        }
    }

    public List<User> getCommonFriends(int id, int friendId) {
        userStorage.checkAvailabilityOfUser(id);
        userStorage.checkAvailabilityOfUser(friendId);

        log.info("Take all common friends of User " + id + " and " + friendId);
        List<User> userFriends = getUserFriends(friendId);
        return getUserFriends(id)
                .stream()
                .filter(userFriends :: contains)
                .collect(Collectors.toList());
    }

    public List<User> getAllUser() {
        log.info("Take all users");
        return userStorage.getAllUser();
    }

    public User getUserById(int id) {
        log.info("Get user id = " + id);
        return userStorage.getUserById(id);
    }

    public List<User> getUserFriends(int id) {
        log.info("Get all user friends id = " + id);
        return getUserById(id)
                .getFriends()
                .stream()
                .map(this::getUserById)
                .collect(Collectors.toList());
    }

    public void checkAvailabilityOfUser(int id) {
        userStorage.checkAvailabilityOfUser(id);
    }
}
