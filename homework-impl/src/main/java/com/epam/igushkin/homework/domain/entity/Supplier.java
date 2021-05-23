package com.epam.igushkin.homework.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private int supplierId;

    @Column(name = "company_name")
    private String companyName;

    @Column
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplier")
    @EqualsAndHashCode.Exclude
    Set<Product> products = new HashSet<>();
}
