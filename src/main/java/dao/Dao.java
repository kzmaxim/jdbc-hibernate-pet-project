package main.java.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    void createTable();
    void dropTable();
    void clearTable();
    int add(T entity);
    int deleteById(Long id);
    List<T> getAll();
    Optional<T> getById(Long id);
    void update(T entity);
}
