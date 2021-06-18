package com.epam.igushkin.homework.services;

import com.epam.igushkin.homework.domain.entity.Customer;

import java.util.List;

/**
 * Описывает методы для сохранения, получения, удаления, изменения заказчика.
 */
public interface CustomerService {

    Customer save(Customer customer);

    List<Customer> getAll();

    Customer findById(Integer id);

    Customer update(Customer customer);

    boolean delete(Integer id);


}
