package com.epam.igushkin.homework.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class ProductDTO {

    private int productId;

    private String productName;

    private Integer supplierId;

    private BigDecimal unitPrice;

    private boolean isDiscontinued;

    private Set<Integer> orders;

}
