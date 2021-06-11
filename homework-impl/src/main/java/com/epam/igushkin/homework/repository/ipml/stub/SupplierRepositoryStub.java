package com.epam.igushkin.homework.repository.ipml.stub;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.repository.Repository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SupplierRepositoryStub implements Repository<Supplier> {
    @Override
    public Optional<Supplier> create(Supplier entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Supplier> read(int id) {
        return Optional.empty();
    }

    @Override
    public List<Supplier> readAll() {
        return null;
    }

    @Override
    public Optional<Supplier> update(Supplier entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
