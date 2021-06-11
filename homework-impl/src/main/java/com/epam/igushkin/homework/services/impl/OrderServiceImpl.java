package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.OrderRepository;
import com.epam.igushkin.homework.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Integer id) {
        var orderOpt = orderRepository.findById(id);
        return orderOpt.orElseThrow(() ->new NoEntityFoundException("Невозможно найти заказ с номером " + id));
    }

    @Override
    public Order update(Integer id, Order order) {
        var oldOrderOpt = orderRepository.findById(id);
        if (oldOrderOpt.isEmpty()) {
            throw new NoEntityFoundException("Заказ с id" + id + "не найден.");
        }
        var oldOrder = oldOrderOpt.get();
        oldOrder.setOrderNumber(order.getOrderNumber())
                .setOrderDate(order.getOrderDate())
                .setTotalAmount(order.getTotalAmount());
        return orderRepository.save(oldOrder);
    }

    @Override
    public boolean delete(Integer id) {
        boolean success = false;
        if (orderRepository.existsById(id)) {
            orderRepository.delete(orderRepository.findById(id).get());
            success = true;
            log.info("delete() - Удаление прошло успешно.");
        } else {
            log.info("delete() - Нечего удалить.");
        }
        return success;
    }

    /*public List<Order> getOrders(Long customerId) {

        return orderRepository.findAllByCustomerId(customerId);
    }*/
}
