package com.epam.igushkin.homework.converters.order;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderToDTOConverter implements Converter<Order, OrderDTO> {

    @Override
    public OrderDTO convert(Order order) {
        var dto = new OrderDTO()
                .setOrderId(order.getOrderId())
                .setCustomerId(order.getCustomer().getCustomerId())
                .setOrderNumber(order.getOrderNumber())
                .setOrderDate(order.getOrderDate().toString())
                .setTotalAmount(order.getTotalAmount());
        log.info("convert() - Order {} to DTO {}", order, dto);
        return dto;
    }
}
