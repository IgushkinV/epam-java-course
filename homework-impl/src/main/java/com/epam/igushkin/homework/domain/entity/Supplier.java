package com.epam.igushkin.homework.domain.entity;

import lombok.Data;

import javax.persistence.*;

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

    @Column(name = "phone")
    private String phone;
}
