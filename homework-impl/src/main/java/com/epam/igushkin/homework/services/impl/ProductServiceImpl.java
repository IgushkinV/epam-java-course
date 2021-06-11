package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.ProductRepository;
import com.epam.igushkin.homework.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        var productOptional = productRepository.findById(id);
        return productOptional.orElseThrow(() -> new NoEntityFoundException("Невозможно найти Продукт с номером " + id));
    }

    @Override
    public Product update(Long id, Product product) {
        var oldProductOpt = productRepository.findById(id);
        if (oldProductOpt.isEmpty()) {
            throw new NoEntityFoundException("Заказ с id" + id + "не найден.");
        }
        var oldProduct = oldProductOpt.get();
        oldProduct.setProductName(product.getProductName())
                .setSupplier(product.getSupplier())
                .setDiscontinued(product.isDiscontinued())
                .setUnitPrice(product.getUnitPrice())
                .setOrders(product.getOrders());
        return productRepository.save(product);
    }

    @Override
    public boolean delete(Long id) {
        boolean success = false;
        if (productRepository.existsById(id)) {
            productRepository.delete(productRepository.findById(id).get());
            success = true;
            log.info("delete() - Удаление прошло успешно.");
        } else {
            log.info("delete() - Нечего удалить.");
        }
        return success;
    }
}
