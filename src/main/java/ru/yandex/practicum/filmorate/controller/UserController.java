package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class UserController extends Controller<User> {
    @GetMapping("/users")
    public List<User> getAll() {
        return super.getAll();
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        log.info("Добавлен пользователь - {}", user);
        return super.create(user);
    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) {
        validate(user);
        if (storage.containsKey(user.getId())) {
            storage.replace(user.getId(), user);
            log.info("Пользователь обновлен - {}", user);
        } else {
            log.info("Пользователь для обновления не найдем - {}", user);
            throw new ValidationException("Пользователь для обновления не найдем");
        }
        return user;
    }

    public void validate(User user) {
        if (user.getLogin() == null || user.getBirthday() == null)
            throw new ValidationException("Ошибка валидации пользователя");

        if (user.getName() == null) user.setName(user.getLogin());

        if (!(user.getBirthday().isBefore(LocalDate.now()) &&
                user.getEmail().contains("@") &&
                !user.getEmail().isEmpty() &&
                !user.getLogin().isEmpty() &&
                !user.getLogin().contains(" "))) {
            log.info("Ошибка валидации пользователя - {}", user);
            throw new ValidationException("Ошибка валидации пользователя");
        }
    }
}
