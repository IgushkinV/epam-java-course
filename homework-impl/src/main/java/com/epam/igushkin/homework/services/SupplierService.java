package com.epam.igushkin.homework.services;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.domain.entity.Supplier;

import java.util.List;

public interface SupplierService {

    Supplier save(Supplier supplier);

    List<Supplier> getAll();

    Supplier findById(Long id);

    Supplier update (Long id, Supplier product);

    boolean delete (Long id);
}
