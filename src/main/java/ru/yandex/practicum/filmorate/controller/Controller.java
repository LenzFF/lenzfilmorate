package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Controller<T extends Model> {
    protected Map<Integer, T> storage = new HashMap<>();

    protected int id = 0;

    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    public T create(T item) {
        validate(item);
        item.setId(++id);
        storage.put(id, item);
        return item;
    }

    public abstract T update(T item);

    public abstract void validate(T item);
}
