package com.epam.igushkin.homework.dto;

import com.epam.igushkin.homework.domain.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class SupplierDTO extends AbstractDTO{

    private int supplierId;

    private String companyName;

    private String phone;

    Set<Product> products;


}
