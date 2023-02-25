package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Model;

import java.util.List;

public interface Storage<T extends Model> {
    List<T> getAll();
    T getById(long id);
    void add(T t);
    void update(T t);
    void deleteAll();
    void deleteById(long id);
}
