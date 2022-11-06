package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.UserValidator;
import ru.yandex.practicum.filmorate.validators.exeption.ValidationException;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
            userStorage.createUser(user);
        } else {
            log.error("Validation error when update object in UserService!");
            throw new ValidationException("Validation error!");
        }
        return user;
    }

    public void removeFriend(int id, int friendId) {
        if (!userStorage.getAllUser().containsKey(id)) {
            log.debug("removeFriend : User with id = " + id + "not found!");
            throw new NoSuchElementException("User with id = " + id + "not found!");
        }

        if (!userStorage.getAllUser().containsKey(friendId)) {
            log.debug("removeFriend : User with id = " + friendId + "not found!");
            throw new NoSuchElementException("User with id = " + friendId + "not found!");
        }

        log.info("Remove friend to user by id = " + id);
        getUserById(id).getFriends().remove(friendId);
        getUserById(friendId).getFriends().remove(id);
    }

    public void addFriend(int id, int friendId) {
        if (!userStorage.getAllUser().containsKey(id)) {
            log.debug("addFriend : User with id = " + id + "not found!");
            throw new NoSuchElementException("User with id = " + id + "not found!");
        }

        if (!userStorage.getAllUser().containsKey(friendId)) {
            log.debug("addFriend : User with id = " + friendId + "not found!");
            throw new NoSuchElementException("User with id = " + friendId + "not found!");
        }

        log.info("Add friend to user by id = " + id);
        getUserById(id).getFriends().add(friendId);
        getUserById(friendId).getFriends().add(id);
    }

    public void updateUser(User user) {
        if (!userStorage.getAllUser().containsKey(user.getId())) {
            log.debug("updateUser : User with id = " + user.getId() + "not found!");
            throw new NoSuchElementException("User with id = " + user.getId() + "not found!");
        }

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
        if (!userStorage.getAllUser().containsKey(id)) {
            log.debug("getCommonFriends : User with id = " + id + "not found!");
            throw new NoSuchElementException("User with id = " + id + "not found!");
        }

        if (!userStorage.getAllUser().containsKey(friendId)) {
            log.debug("getCommonFriends : User with id = " + friendId + "not found!");
            throw new NoSuchElementException("User with id = " + friendId + "not found!");
        }

        log.info("Take all common friends of User " + id + " and " + friendId);
        return getUserFriends(id)
                .stream()
                .filter(getUserFriends(friendId) :: contains)
                .collect(Collectors.toList());
    }

    public Map<Integer, User> getAllUser() {
        log.info("Take all users");
        return userStorage.getAllUser();
    }

    public User getUserById(int id) {
        if (!userStorage.getAllUser().containsKey(id)) {
            log.debug("User with id = " + id + "not found!");
            throw new NoSuchElementException("User with id = " + id + "not found!");
        }

        log.info("Get user id = " + id);
        return userStorage.getAllUser().get(id);
    }

    public List<User> getUserFriends(int id) {
        if (userStorage.getAllUser().isEmpty()) {
            log.debug("User's friends list with id = " + id + " is empty!");
            throw new NoSuchElementException("User's friends list with id = " + id + " is empty!");
        }

        log.info("Get all user friends id = " + id);
        return getUserById(id)
                .getFriends()
                .stream()
                .map((userId) -> getUserById(userId))
                .collect(Collectors.toList());
    }
}
