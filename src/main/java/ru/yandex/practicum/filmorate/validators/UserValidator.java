package ru.yandex.practicum.filmorate.validators;

import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserValidator {

    final static LocalDate now = LocalDate.now();

    public boolean validate(User user) {
        boolean approveUser = true;

        if (!user.getEmail().contains("@") || user.getEmail().isEmpty() || user.getEmail() == null) {
            approveUser = false;
        } else if (user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            approveUser = false;
        } else if (now.isBefore(user.getBirthday())) {
            approveUser = false;
        }

        return approveUser;
    }
}
