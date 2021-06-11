package com.epam.igushkin.homework.repository.ipml.stub;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.repository.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CustomerRepositoryStub implements Repository<Customer> {
    @Override
    public Optional<Customer> create(Customer entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> read(int id) {
        return Optional.empty();
    }

    @Override
    public List<Customer> readAll() {
        return null;
    }

    @Override
    public Optional<Customer> update(Customer entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
