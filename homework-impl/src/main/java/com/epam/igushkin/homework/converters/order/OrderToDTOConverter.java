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
        var dto = OrderDTO.builder()
                .customer(order.getCustomer())
                .orderNumber(order.getOrderNumber())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .build();
        log.info("convert() - Order {} to DTO {}", order, dto);
        return dto;
    }
}
