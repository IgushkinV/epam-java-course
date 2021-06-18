package com.epam.igushkin.homework.services;

import com.epam.igushkin.homework.domain.entity.Supplier;

import java.util.List;

/**
 * Описывает методы для сохранения, получения, удаления, изменения поставщика.
 */
public interface SupplierService {

    Supplier save(Supplier supplier);

    List<Supplier> getAll();

    Supplier findById(Integer id);

    Supplier update(Supplier supplier);

    boolean delete(Integer id);
}
