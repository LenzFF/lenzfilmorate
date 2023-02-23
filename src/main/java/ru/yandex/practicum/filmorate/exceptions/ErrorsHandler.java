package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.util.Map;

@RestControllerAdvice (value = "ru.yandex.practicum.filmorate")
public class ErrorsHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> validationError(final ValidationException e) {
        return Map.of("Validation error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFoundError(final NotFoundException e) {
        return Map.of("Not found error", e.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,String> runtimeErrors(final RuntimeException e) {
        return Map.of("Server error", e.getMessage());
    }
}
