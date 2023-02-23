package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractStorage<T extends Model> implements Storage<T> {

    private Map<Long, T> storage = new HashMap<>();

    private long id = 0;

    @Override
    public List<T> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public T getById(long id) {
        return storage.get(id);
    }

    @Override
    public void add(T t) {
        id++;
        t.setId(id);
        storage.put(id, t);
    }

    @Override
    public void update(T t) {
        storage.replace(t.getId(), t);
    }

    @Override
    public void deleteAll() {
        id = 0;
        storage.clear();
    }

    @Override
    public void deleteById(long id) {
        storage.remove(id);
    }
}
