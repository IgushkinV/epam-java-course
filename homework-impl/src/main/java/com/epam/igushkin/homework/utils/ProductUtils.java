package com.epam.igushkin.homework.utils;

import com.epam.igushkin.homework.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ProductUtils {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");

    public void create(Product product) {
        EntityManager entityManager = emf.createEntityManager();
        log.info("create() - Запись в БД объекта {}", product);
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Optional<Product> read(int id) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Product product = entityManager.find(Product.class, id);
        entityManager.getTransaction().commit();
        log.info("read() - Считано из БД: {}", product);
        entityManager.close();
        return Optional.ofNullable(product);
    }

    public boolean update (int id) {
        EntityManager entityManager = emf.createEntityManager();
        boolean success = false;
        entityManager.getTransaction().begin();
        Product product = entityManager.find(Product.class, id);
        if (Objects.nonNull(product)) {
            product.setProductName(product.getProductName() + " - Updated");
            success = true;
            log.info("update() - Изменение прошло успешно.");
        } else {
            log.warn("update() - Попытка изменить несуществующую запись!");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return success;
    }

    public boolean delete (int id) {
        EntityManager entityManager = emf.createEntityManager();
        boolean success = false;
        entityManager.getTransaction().begin();
        Product product = entityManager.find(Product.class, id);
        if (Objects.nonNull(product)) {
            entityManager.remove(product);
            success = true;
            log.info("delete() - Удаление прошло успешно");
        } else {
            log.warn("delete() - Попытка удалить несуществующую запись!");
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return success;
    }
}
