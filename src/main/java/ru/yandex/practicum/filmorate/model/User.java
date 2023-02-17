package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends Model{
    private String name;
    @Past
    private LocalDate birthday;
    @NotBlank
    private String login;
    @NonNull
    @Email
    private String email;
}
