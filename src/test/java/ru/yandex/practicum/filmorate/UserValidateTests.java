package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.controller.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidateTests {
    UserController userController = new UserController();

    @Test
    void addUserTest() {
        User user = new User();
        user.setLogin("Lenz");
        user.setName("Яков Порушков");
        user.setEmail("lenzff@yandex.ru");
        user.setBirthday(LocalDate.of(1988, 1, 11));
        userController.validate(user);
    }

    @Test
    void emptyFieldsTets() {
        User user = new User();
        assertThrows(ValidationException.class, () -> userController.validate(user));
    }

    @Test
    void emptyNameTest() {
        User user = new User();
        user.setLogin("Lenz");
        user.setEmail("lenzff@yandex.ru");
        user.setBirthday(LocalDate.of(1988, 1, 11));
        userController.validate(user);
        assertEquals(user.getName(), user.getLogin());
    }

    @Test
    void futureBirthdayTest() {
        User user = new User();
        user.setLogin("Lenz");
        user.setName("Яков Порушков");
        user.setEmail("lenzff@yandex.ru");
        user.setBirthday(LocalDate.of(2088, 1, 11));
        assertThrows(ValidationException.class, () -> userController.validate(user));
    }

    @Test
    void emailTest() {
        User user = new User();
        user.setLogin("Lenz");
        user.setName("Яков Порушков");
        user.setEmail("lenzffyandex.ru");
        user.setBirthday(LocalDate.of(1988, 1, 11));
        assertThrows(ValidationException.class, () -> userController.validate(user));
    }

    @Test
    void loginWithSpacesTest() {
        User user = new User();
        user.setLogin("  Lenz  ");
        user.setName("Яков Порушков");
        user.setEmail("lenzff@yandex.ru");
        user.setBirthday(LocalDate.of(1988, 1, 11));
        assertThrows(ValidationException.class, () -> userController.validate(user));
    }
}
