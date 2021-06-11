package com.epam.igushkin.homework.services;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {
    Product save(Product product);

    List<Product> getAll();

    Product findById(Long id);

    Product update (Long id, Product product);

    boolean delete (Long id);

}
