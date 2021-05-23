package com.epam.igushkin.homework.services;

import java.util.Optional;

public interface CRUDService<T> {

    public void create(T entity);

    public Optional<T> retrieve(int id);

    public boolean update(int id);

    public boolean delete(int id);


}
