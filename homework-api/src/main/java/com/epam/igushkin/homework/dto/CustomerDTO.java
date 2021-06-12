package com.epam.igushkin.homework.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerDTO {

    private int customerId;

    private String customerName;

    private String phone;

    private List<Integer> orders;
}
