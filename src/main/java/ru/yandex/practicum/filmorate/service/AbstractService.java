package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

public abstract class AbstractService<T extends Model> {
    Storage<T> storage;

    public void create(T data) {
        storage.add(data);
    }

    public T get(long id) {
        return storage.getById(id);
    }

    public List<T> getAll() {
        return storage.getAll();
    }

    public void update(T data) {
        storage.update(data);
    }

    public void delete(long id) {
        storage.deleteById(id);
    }

    public void deleteAll() {
        storage.deleteAll();
    }

    protected abstract void validate(T data);
}
