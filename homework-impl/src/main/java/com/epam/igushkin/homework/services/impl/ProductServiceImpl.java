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

    /**
     * Сохраняет продукт в репозитроий.
     *
     * @param product продукт для сохранения.
     * @return сохраненный продукт.
     */
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Возвращает все продукты из репозитория.
     *
     * @return список продуктов.
     */
    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    /**
     * Находит продукт по его id в репозитории.
     *
     * @param id уникальный номер продукта.
     * @return найденный продукт.
     */
    @Override
    public Product findById(Integer id) {
        var productOptional = productRepository.findById(id);
        return productOptional.orElseThrow(() -> new NoEntityFoundException("Невозможно найти Продукт с номером " + id));
    }

    /**
     * Обновляет данные продукт в репозитории.
     *
     * @param id уникальный номер продукта.
     * @param product данные для обновления продукта.
     * @return обновленный продукт.
     */
    @Override
    public Product update(Integer id, Product product) {
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

    /**
     * Удаляет продукт из репозитория по id.
     *
     * @param id уникальный номер продукта.
     * @return результат удаления (true или false).
     */
    @Override
    public boolean delete(Integer id) {
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
