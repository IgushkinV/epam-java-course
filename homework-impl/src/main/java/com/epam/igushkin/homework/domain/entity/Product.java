package com.epam.igushkin.homework.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;


    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "is_discontinued")
    private boolean isDiscontinued;

    @ManyToMany(mappedBy = "products")
    @ToString.Exclude
    private Set<Order> orders;
}
