package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class Film {
    private int id;
    @NotBlank
    private String name;
    @NonNull
    private String description;
    private LocalDateTime releaseDate;
    private int duration;
}

