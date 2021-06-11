package com.epam.igushkin.homework.repository.ipml.stub;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.repository.Repository;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryStub implements Repository<Order> {
    @Override
    public Optional<Order> create(Order entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> read(int id) {
        return Optional.empty();
    }

    @Override
    public List<Order> readAll() {
        return null;
    }

    @Override
    public Optional<Order> update(Order entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
