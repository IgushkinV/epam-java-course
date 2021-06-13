package com.epam.igushkin.homework.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDTO {

    private int orderId;

    private String orderNumber;

    private Integer customerId;

    private String orderDate;

    private BigDecimal totalAmount;

    private Set<Integer> products;
}
