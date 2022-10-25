package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class User {
    private int id;
    private String email;
    @NonNull
    @NotBlank
    private String login;
    private String name;
    private LocalDateTime birthday;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{");
        sb.append("id=" + id);
        sb.append(", email='" + email);
        sb.append(", login='" + login);
        if (name.isEmpty()) {
            sb.append(", name='" + login);
        } else {
            sb.append(", name='" + name);
        }
        sb.append(", birthday=" + birthday);
        sb.append("}");

        return sb.toString();
    }
}
