package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.ProductRepository;
import com.epam.igushkin.homework.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Autowired
    private MessageSource errorSource;

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
        return (List<Product>) productRepository.findAll();
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
        Object[] args = {id.intValue()};
        var message = "Продукт не найден.";
        //String exceptionMessageLocale = errorSource.getMessage("noProduct", args, Locale.getDefault());
        return productOptional.orElseThrow(() -> new NoEntityFoundException(message));
    }

    /**
     * Обновляет данные продукт в репозитории.
     *
     * @param product данные для обновления продукта.
     * @return обновленный продукт.
     */
    @Override
    public Product update(Product product) {
        var id = product.getProductId();
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
