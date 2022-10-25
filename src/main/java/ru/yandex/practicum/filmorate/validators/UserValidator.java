package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDateTime;

public class UserValidator {

    public boolean validate(User user) {
        boolean approveUser = true;

        LocalDateTime now = LocalDateTime.now();

        if (!user.getEmail().contains("@") || user.getEmail().isEmpty()) {
            approveUser = false;
        } else if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            approveUser = false;
        } else if (now.isBefore(user.getBirthday())) {
            approveUser = false;
        }

        return approveUser;
    }
}
