package com.epam.igushkin.homework.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
    public T create(T entity);

    public Optional<T> read(int id);

    public List<T> readAll();

    public boolean update(T entity);

    public boolean delete(int id);
}
