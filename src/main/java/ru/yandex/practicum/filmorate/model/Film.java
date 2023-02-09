package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Film extends Model{
    @NotBlank
    private String name;
    private LocalDate releaseDate;
    @Positive
    private int duration;
    @Size(min = 1, max = 200)
    private String description;
}
