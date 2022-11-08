package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.exeption.NoFoundElementException;

import java.util.HashMap;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private static HashMap<Integer, User> usersTracker;
    private int usersTrackerCounter;

    public InMemoryUserStorage() {
        this.usersTracker = new HashMap<>();
    }

    @Override
    public User createUser(User user) {
        log.debug("Create new object in userTracker " + user);
        if (user.getId() == 0) {
            user.setId(addCounter());
        }
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        usersTracker.put(user.getId(), user);

        return user;
    }

    @Override
    public User removeUser(User user) {
        if (!usersTracker.containsKey(user.getId()) || user.getId() == 0) {
            throw new NoFoundElementException("user not found!");
        }

        usersTracker.remove(user.getId());
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (!usersTracker.containsKey(user.getId()) || user.getId() == 0) {
            throw new NoFoundElementException("user not found!");
        }

        log.debug("Update object in userTracker " + user);
        if (user.getName().isEmpty()) {
            user.setName(user.getLogin());
        } else {
            user.setName(user.getName());
        }
        usersTracker.put(user.getId(), user);

        return user;
    }

    @Override
    public HashMap<Integer, User> getAllUser() {
        return usersTracker;
    }

    private int addCounter(){
        return ++usersTrackerCounter;
    }
}
