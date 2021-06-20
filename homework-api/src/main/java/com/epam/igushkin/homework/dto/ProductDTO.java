package com.epam.igushkin.homework.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDTO {

    private int productId;

    private String productName;

    private Integer supplierId;

    private BigDecimal unitPrice;

    private boolean isDiscontinued;

    private Set<Integer> orders;

}
