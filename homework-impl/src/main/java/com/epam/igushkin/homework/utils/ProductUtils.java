package com.epam.igushkin.homework.utils;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ProductUtils {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");

    public Product create(String productName, int supplierId, BigDecimal unitPrice, boolean isDiscontinued) {
        var entityManager = emf.createEntityManager();
        var product = new Product();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            product.setProductName(productName);
            product.setSupplier(new SupplierUtils().read(supplierId).get());
            product.setDiscontinued(isDiscontinued);
            product.setUnitPrice(unitPrice);
            entityManager.persist(product);
            transaction.commit();
            log.info("create() - Запись в БД объекта {}", product);
        } catch (Exception e) {
            log.error("create() - Ошибка при создании и записи нового объекта.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return product;
    }

    public Optional<Product> read(int id) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();;
        Product product = null;
        try {
            transaction.begin();
            product = entityManager.find(Product.class, id);
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("read() - Ошибка во время чтения записи из БД.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        log.info("read() - Считано из БД: {}", product);
        return Optional.ofNullable(product);
    }

    public List<Product> readAll() {
        var entityManager = emf.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> rootEntry = cq.from(Product.class);
        CriteriaQuery<Product> all = cq.select(rootEntry);
        TypedQuery<Product> allQuery = entityManager.createQuery(all);
        var resultList = allQuery.getResultList();
        entityManager.close();
        return resultList;
    }

    public boolean update (int id, String productName, int supplierId, BigDecimal unitPrice, boolean isDiscontinued) {
        var entityManager = emf.createEntityManager();
        var success = false;
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var product = entityManager.find(Product.class, id);
            if (Objects.nonNull(product)) {
                product.setProductName(productName);
                product.setUnitPrice(unitPrice);
                product.setSupplier(new SupplierUtils().read(supplierId).get());
                product.setDiscontinued(isDiscontinued);
                success = true;
                log.info("update() - Изменение прошло успешно.");
            } else {
                log.warn("update() - Попытка изменить несуществующую запись!");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("update() - Ошибка при попытке обновления записи.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return success;
    }

    public boolean delete (int id) {
        var entityManager = emf.createEntityManager();
        var success = false;
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var product = entityManager.find(Product.class, id);
            if (Objects.nonNull(product)) {
                entityManager.remove(product);
                success = true;
                log.info("delete() - Удаление прошло успешно");
            } else {
                log.warn("delete() - Попытка удалить несуществующую запись!");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("delete() - Ошибка при попытке удалить запись из БД.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return success;
    }
}
