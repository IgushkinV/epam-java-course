package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.repository.ipml.CustomerRepository;
import com.epam.igushkin.homework.services.CRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerCRUDServiceImpl implements CRUDService<Customer> {

    private final CustomerRepository customerRepository;

    @Override
    public Optional<Customer> create(JSONObject jsonObject) {
        var name = jsonObject.getString("customer_name");
        var phone = jsonObject.getString("phone");
        var customer = new Customer();
        customer.setCustomerName(name);
        if (Objects.nonNull(phone)) {
            customer.setPhone(phone);
        }
        customerRepository.create(customer);
        return customerRepository.read(customer.getCustomerId());
    }

    @Override
    public Optional<Customer> read(int id) {
        return customerRepository.read(id);
    }

    @Override
    public List<Customer> readAll() {
        return customerRepository.readAll();
    }

    @Override
    public Optional<Customer> update(int id, JSONObject jsonObject) {
        var customerName = jsonObject.getString("customer_name");
        var phone = jsonObject.getString("phone");
        var customer = new Customer();
        customer.setCustomerId(id);
        customer.setCustomerName(customerName);
        customer.setPhone(phone);
        return customerRepository.update(customer);
    }

    @Override
    public boolean delete(int id) {
        return customerRepository.delete(id);
    }
}
