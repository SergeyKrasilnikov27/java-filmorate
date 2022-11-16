package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;
import java.util.Map;

public interface UserStorage {
    User createUser(User user);

    User removeUser(User film);

    User updateUser(User film);

    List<User> getAllUser();

    void checkAvailabilityOfUser(int id);

    User gitUserById(int id);

    Map<Integer, User> getUsersTracker();
}
