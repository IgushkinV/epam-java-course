package com.epam.igushkin.homework.dto;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class OrderDTO extends AbstractDTO{

    private int orderId;

    private String orderNumber;

    private Customer customer;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private Set<Product> products;
}
