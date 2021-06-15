package com.epam.igushkin.homework.resources.impl;
import com.epam.igushkin.homework.converters.order.DtoToOrderConverter;
import com.epam.igushkin.homework.converters.order.OrderToDTOConverter;
import com.epam.igushkin.homework.dto.OrderDTO;
import com.epam.igushkin.homework.resources.OrderResource;
import com.epam.igushkin.homework.services.impl.CustomerServiceImpl;
import com.epam.igushkin.homework.services.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderResourceImpl implements OrderResource {

    private final OrderServiceImpl orderService;
    private final CustomerServiceImpl customerService;
    private final OrderToDTOConverter orderToDTOConverter;
    private final DtoToOrderConverter dtoToOrderConverter;

    /**
     * Получает Заказ по id из базы.
     *
     * @param id уникальный номер заказа в базе.
     * @return  OrderDTO, содержит данные заказа, считанного из базы.
     */
    @Override
    public OrderDTO get(Integer id) {
        var order = orderService.findById(id);
        log.info("get() - {}", order);
        return orderToDTOConverter.convert(order);
    }

    /**
     * Получает все заказы из базы.
     *
      * @return
     */
    @Override
    public List<OrderDTO> getAllOrders() {
        var orders = orderService.getAll().stream()
                .map(orderToDTOConverter::convert)
                .collect(Collectors.toList());
        log.info("getAllOrders() - {}", orders);
        return orders;
    }

    /**
     * Добавляет заказ в базу.
     *
     * @param orderDTO содержит данные заказа, который нужно добавить.
     * @return OrderDTO с данными добавленного заказа.
     */
    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        var order = dtoToOrderConverter.convert(orderDTO);
        var savedCustomerDTO = orderToDTOConverter.convert(orderService.save(order));
        log.info("addOrder() - {}", savedCustomerDTO);
        return savedCustomerDTO;
    }

    /**
     * Обновляет данные заказчика по id.
     * @param orderDTO содержир данные заказа, которые нужно обновить.
     * @return OrderDTO с обновленным данными заказа.
     */
    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        var order = orderService.findById(orderDTO.getOrderId());
        order.setOrderDate(LocalDateTime.parse(orderDTO.getOrderDate()))
                .setTotalAmount(orderDTO.getTotalAmount())
                .setOrderNumber(orderDTO.getOrderNumber())
                .setCustomer(customerService.findById(orderDTO.getCustomerId()));
        var updatedOrder = orderService.save(order);
        log.info("updateOrder() - {}", updatedOrder);
        return orderToDTOConverter.convert(updatedOrder);
    }

    /**
     * Удаляет заказ из базы.
     * @param id уникальыне номер заказа.
     * @return результат удаления (true или false).
     */
    @Override
    public boolean delete(Integer id) {
        boolean result = orderService.delete(id);
        log.info("delete() - {}", result);
        return result;
    }
}
