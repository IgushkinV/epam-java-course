package com.epam.igushkin.homework.services;

import com.epam.igushkin.homework.domain.entity.Order;

import java.util.List;

/**
 * Описывает методы для сохранения, получения, удаления, изменения заказа.
 */
public interface OrderService {

    Order save(Order order);

    List<Order> getAll();

    Order findById(Integer id);

    Order update (Integer id, Order customer);

    boolean delete (Integer id);

    /*List<Order> getOrders (Long customerId);*/
}
