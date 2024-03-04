package ru.oop.task.repository;

import ru.oop.task.model.domain.Database;
import ru.oop.task.model.domain.User;

import java.util.List;

public abstract class BaseRepository<T> implements Repository<T> {
    private final Database<T> database;

    public BaseRepository(Database<T> database) {
        this.database = database;
    }

    public List<T> selectAll() {
        return database.selectAll();
    }

    public T select(Integer id) {
        return database.select();
    }

    public void insert(T entity) {
        database.insert(entity);
    }

    public void delete (Integer id) {
        database.delete(id);
    }

    public void update (T entity) {
        database.update(entity);
    }


    public Integer getCurrentId() {
        return database.getCurrentId();
    }

    public abstract void update(T entity, String fieldToUpdate, String valueToUpdate);
}

