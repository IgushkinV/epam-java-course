package com.epam.igushkin.homework.services;

import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

public interface CRUDService<T> {

    Optional<T> create(JSONObject jsonObject);

    Optional<T> read(int id);

    List<T> readAll();

    Optional<T> update(int id, JSONObject jsonObject);

    boolean delete(int id);
}
