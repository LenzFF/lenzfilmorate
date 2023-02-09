package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
public class FilmController extends Controller<Film> {

    @GetMapping("/films")
    public List<Film> getAll() {
        return super.getAll();
    }

    @PostMapping("/films")
    public Film create(@RequestBody Film film) {
        log.info("Добавлен фильм - {}", film);
        return super.create(film);
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) {
        validate(film);
        if (storage.containsKey(film.getId())) {
            storage.replace(film.getId(), film);
            log.info("Фильм обновлен - {}", film);
        } else {
            log.info("Фильм для обновления не найдем - {}", film);
            throw new ValidationException("Фильм для обновления не найдем");
        }
        return film;
    }

    public void validate(Film film) {
        if (film.getName() == null ||
                film.getDescription() == null ||
                film.getReleaseDate() == null)
            throw new ValidationException("Ошибка валидации фильма");

        if (!(film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 27)) &&
                !film.getName().isEmpty() &&
                film.getDescription().length() <= 200 &&
                film.getDuration() > 0)) {
            log.info("Ошибка валидации фильма - {}", film);
            throw new ValidationException("Ошибка валидации фильма");
        }
    }
}
