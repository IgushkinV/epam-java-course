package com.epam.igushkin.homework.dto;

import com.epam.igushkin.homework.domain.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerDTO extends AbstractDTO{

    private int customerId;

    private String customerName;

    private String phone;

    private List<Order> orders;
}
