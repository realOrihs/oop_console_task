package ru.oop.task.repository;

import java.util.List;

public interface Repository<T> {
    List<T> selectAll();
    T select(Integer id);
    void insert(T entity);
    void update(T entity);
    void delete(Integer id);
}
