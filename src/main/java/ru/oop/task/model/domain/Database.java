package ru.oop.task.model.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database<T> {
    private final Map<Integer, T> map;
    private Integer currentId = 0;
    public Database() {
        this.map = new HashMap<>();
    }

    public T select() {
        return null;
    }

    public void insert(T entity) {
        currentId++;
        map.put(currentId, entity); // -> добавить проверку на эксепшн, удостовериться что куррентайди не увеличится если произошла ошибка
    }
    public void update(T entity) {
    }
    public void delete(Integer id) {
        map.remove(id);
    }
    public List<T> selectAll() {
        return new ArrayList<>(map.values());
    }

    public Integer getCurrentId() {
        return currentId;
    }
}

