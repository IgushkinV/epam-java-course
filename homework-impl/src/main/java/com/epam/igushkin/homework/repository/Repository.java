package com.epam.igushkin.homework.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> create(T entity);

    Optional<T> read(int id);

    List<T> readAll();

    Optional<T> update(T entity);

    boolean delete(int id);
}
