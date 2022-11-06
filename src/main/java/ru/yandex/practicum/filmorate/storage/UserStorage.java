package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

public interface UserStorage {
    public User createUser(User user);
    public User removeUser(User film);
    public User updateUser(User film);
    public Map<Integer, User> getAllUser();
}
