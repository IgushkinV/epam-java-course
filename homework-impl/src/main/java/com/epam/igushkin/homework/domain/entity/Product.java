package com.epam.igushkin.homework.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "supplier_Id")
    private int supplierId;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "Is_discontinued")
    private boolean isDiscontinued;

    @ManyToMany(mappedBy = "products")
    private Set<Order> orders;
}
