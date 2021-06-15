package com.epam.igushkin.homework.services;

import com.epam.igushkin.homework.domain.entity.Product;

import java.util.List;

/**
 * Описывает методы для сохранения, получения, удаления, изменения продукта.
 */
public interface ProductService {
    Product save(Product product);

    List<Product> getAll();

    Product findById(Integer id);

    Product update (Integer id, Product product);

    boolean delete (Integer id);

}
