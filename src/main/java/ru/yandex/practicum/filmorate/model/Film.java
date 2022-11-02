package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class Film {
    private int id;
    @NotBlank
    private String name;
    @NonNull
    private String description;
    @NonNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime releaseDate;
    private int duration;
    private int rate;
}

