package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmValidateTests {
    FilmController filmController = new FilmController();

    @Test
    void descriptionOver200Test() {
        Film film = new Film();
        film.setName("First Blood");
        film.setDescription("Он - эксперт. Эксперт по оружию, ножам и собственному телу. " +
                "Он человек, специально обученный не замечать боль и погодные условия. " +
                "На войне он был героем, а на родине он никому не нужен. " +
                "Неспособный приспособиться к мирной жизни Рэмбо путешествует по стране, но его задерживает провинциальный шериф, ненавидящий бродяг. " +
                "Попав в тюрьму и вкусив унижений со стороны полиции, Рэмбо объявляет войну беспределу официальных структур и главе местной полиции лично.");
        film.setDuration(93);
        film.setReleaseDate(LocalDate.of(1982, 10, 22));
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }

    @Test
    void addFilmTest() {
        Film film = new Film();
        film.setName("First Blood");
        film.setDescription("Он - эксперт. Эксперт по оружию, ножам и собственному телу. ");
        film.setDuration(93);
        film.setReleaseDate(LocalDate.of(1982, 10, 22));
        filmController.validate(film);
    }

    @Test
    void emptyFieldsTets() {
        Film film = new Film();
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }

    @Test
    void badReleaseDate() {
        Film film = new Film();
        film.setName("First Blood");
        film.setDescription("Он - эксперт. Эксперт по оружию, ножам и собственному телу. ");
        film.setDuration(93);
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }

    @Test
    void zeroFilmDuration() {
        Film film = new Film();
        film.setName("First Blood");
        film.setDescription("Он - эксперт. Эксперт по оружию, ножам и собственному телу. ");
        film.setDuration(0);
        film.setReleaseDate(LocalDate.of(1982, 10, 22));
        assertThrows(ValidationException.class, () -> filmController.validate(film));
    }
}
