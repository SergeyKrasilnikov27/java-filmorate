package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

public interface UserStorage {
    User createUser(User user);

    User removeUser(User film);

    User updateUser(User film);

    List<User> getAllUser();

    void checkAvailabilityOfUser(int id);

    User getUserById(int id);
}
