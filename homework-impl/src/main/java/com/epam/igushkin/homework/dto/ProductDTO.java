package com.epam.igushkin.homework.dto;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Supplier;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
public class ProductDTO extends  AbstractDTO{

    private int productId;

    private String productName;

    private Supplier supplier;

    private BigDecimal unitPrice;

    private boolean isDiscontinued;

    private Set<Order> orders;

}
