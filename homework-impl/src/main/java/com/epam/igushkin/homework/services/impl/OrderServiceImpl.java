package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.logger.Logging;
import com.epam.igushkin.homework.repository.OrderRepository;
import com.epam.igushkin.homework.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final MessageSource errorSource;

    /**
     * Сохраняет заказ в репозитории.
     *
     * @param order
     * @return Order сохраненный заказ.
     */
    @Logging
    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Получает все заказы из репозитория.
     *
     * @return List<Order> - список заказов.
     */
    @Override
    public List<Order> getAll() {
        return (List<Order>) orderRepository.findAll();
    }

    /**
     * Ищет заказ по его id в репозитории.
     *
     * @param id уникальный номер заказа.
     * @return Order - найденный заказ.
     */
    @Override
    public Order findById(Integer id) {
        var orderOpt = orderRepository.findById(id);
        Object[] args = {id.intValue()};
        String exceptionMessageLocale = errorSource.getMessage("noOrder", args, Locale.getDefault());
        return orderOpt.orElseThrow(() -> new NoEntityFoundException(exceptionMessageLocale));
    }

    /**
     * Обновляет данные заказа в репозитории.
     *
     * @param order данные для обновления заказа.
     * @return обновленынй заказ.
     */
    @Override
    public Order update(Order order) {
        var id = order.getOrderId();
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

    /**
     * Удаляет заказ из репоозитория по его id.
     *
     * @param id уникальный номер заказа.
     * @return результат удаления (true или false).
     */
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
}
