package com.epam.igushkin.homework.converters.order;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.dto.OrderDTO;
import com.epam.igushkin.homework.services.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class DtoToOrderConverter implements Converter<OrderDTO, Order> {

    private final CustomerServiceImpl customerService;
    @Override
    public Order convert(OrderDTO orderDTO) {
        var order = new Order()
                .setOrderNumber(orderDTO.getOrderNumber())
                .setOrderDate(LocalDateTime.parse(orderDTO.getOrderDate()))
                .setTotalAmount(orderDTO.getTotalAmount())
                .setCustomer(customerService.findById(orderDTO.getCustomerId()));
        log.info("convert() - DTO {} to Order {}", orderDTO, order);
        return order;
    }
}
