package com.epam.igushkin.homework.repository.ipml.stub;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.repository.Repository;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryStub implements Repository<Product> {
    @Override
    public Optional<Product> create(Product entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> read(int id) {
        return Optional.empty();
    }

    @Override
    public List<Product> readAll() {
        return null;
    }

    @Override
    public Optional<Product> update(Product entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
