package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {
    User createUser(User user);
    User removeUser(User film);
    User updateUser(User film);
    Map<Integer, User> getAllUser();
}
