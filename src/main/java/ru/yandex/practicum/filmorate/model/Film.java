package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
    private Set<Long> likes = new TreeSet<>();

    public int getLikesCount() {
        return likes.size();
    }
}
