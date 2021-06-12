package com.epam.igushkin.homework.converters.order;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DtoToOrderConverter implements Converter<OrderDTO, Order> {
    @Override
    public Order convert(OrderDTO orderDTO) {
        var order = new Order()
                .setOrderNumber(orderDTO.getOrderNumber())
                .setOrderDate(orderDTO.getOrderDate())
                .setTotalAmount(orderDTO.getTotalAmount());
        log.info("convert() - DTO {} to Order {}", orderDTO, order);
        return order;
    }
}
