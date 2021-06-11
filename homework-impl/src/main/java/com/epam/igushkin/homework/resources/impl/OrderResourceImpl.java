package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.converters.order.DtoToOrderConverter;
import com.epam.igushkin.homework.converters.order.OrderToDTOConverter;
import com.epam.igushkin.homework.dto.OrderDTO;
import com.epam.igushkin.homework.resources.OrderResource;
import com.epam.igushkin.homework.services.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderResourceImpl implements OrderResource {

    private final OrderServiceImpl orderService;
    private final OrderToDTOConverter orderToDTOConverter;
    private final DtoToOrderConverter dtoToOrderConverter;


    @Override
    public OrderDTO get(Integer id) {
        var orderDTO = orderService.findById(id);
        return null;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        var orders = orderService.getAll().stream()
                .map(orderToDTOConverter::convert)
                .collect(Collectors.toList());
        log.info("getAllOrders() - {}", orders);
        return orders;
    }

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) {
        var order = dtoToOrderConverter.convert(orderDTO);
        var savedCustomerDTO = orderToDTOConverter.convert(orderService.save(order));
        log.info("addOrder() - {}", savedCustomerDTO);
        return savedCustomerDTO;
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        var order = orderService.findById(orderDTO.getOrderId());
        order.setOrderDate(orderDTO.getOrderDate())
                .setTotalAmount(orderDTO.getTotalAmount())
                .setOrderNumber(orderDTO.getOrderNumber());
        var updatedOrder = orderService.save(order);
        log.info("updateCustomer() - {}", updatedOrder);
        return orderToDTOConverter.convert(updatedOrder);
    }

    @Override
    public boolean delete(Integer id) {
        boolean result = orderService.delete(id);
        log.info("deleteCustomer() - {}", result);
        return result;
    }
}
