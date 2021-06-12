package com.epam.igushkin.homework.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class OrderDTO {

    private int orderId;

    private String orderNumber;

    private Integer customerId;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private Set<Integer> products;
}
