package com.epam.igushkin.homework.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Component
@Data
@Entity
@Table
@Accessors(chain = true)
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private int supplierId;

    @Column(name = "company_name")
    private String companyName;

    @Column
    private String phone;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "supplier")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<Product> products = new HashSet<>();
}
