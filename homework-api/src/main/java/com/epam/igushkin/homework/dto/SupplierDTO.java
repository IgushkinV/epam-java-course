package com.epam.igushkin.homework.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SupplierDTO {

    private int supplierId;

    private String companyName;

    private String phone;

    Set<Integer> products;


}
