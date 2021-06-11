package com.epam.igushkin.homework.services;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customer);

    List<Customer> getAll();

    Customer findById(Integer id);

    Customer update (Integer id, Customer customer);

    boolean delete (Integer id);


}
