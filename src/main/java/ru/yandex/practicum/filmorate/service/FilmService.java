package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService extends AbstractService<Film>{

    Storage<User> userStorage;

    public static Comparator<Film> FILM_COMPARATOR = Comparator.comparing(Film::getLikesCount).reversed();

    @Autowired
    public FilmService(Storage<Film> filmStorage, Storage<User> userStorage) {
        this.storage = filmStorage;
        this.userStorage =  userStorage;
    }

    private Film getFilmOrThrowException(long id) {
        Film film = storage.getById(id);
        if (film == null) {
            log.info("Фильм не найден - {}", id);
            throw new NotFoundException("Фильм не найден");
        }
        return film;
    }

    public void addLike(Long filmId, Long userId) {
        Film film = getFilmOrThrowException(filmId);

        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        }

        film.getLikes().add(userId);
        userStorage.getById(userId).getLikes().add(filmId);
    }

    public void removeLike(Long filmId, Long userId) {
        Film film = getFilmOrThrowException(filmId);

        if (userStorage.getById(userId) == null) {
            throw new NotFoundException("Пользователь не найден");
        }

        film.getLikes().remove(userId);
        userStorage.getById(userId).getLikes().remove(filmId);
    }

    @Override
    public void create(Film data) {
        validate(data);
        super.create(data);
    }

    public List<Film> topLikedFilms(Long max) {
        return storage.getAll().stream()
                .sorted(FILM_COMPARATOR)
                .limit(max)
                .collect(Collectors.toList());
    }

    @Override
    public Film get(long id) {
        Film film = getFilmOrThrowException(id);
        return film;
    }

    @Override
    public void update(Film data) {
        getFilmOrThrowException(data.getId());
        validate(data);
        super.update(data);
    }

    @Override
    public void delete(long id) {
        getFilmOrThrowException(id);
        super.delete(id);
    }

    @Override
    protected void validate(Film film) {
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
