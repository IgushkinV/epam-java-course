package com.epam.igushkin.homework.utils;

import com.epam.igushkin.homework.domain.entity.Supplier;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class SupplierUtils {

    private final EntityManager entityManager;

    public SupplierUtils() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        entityManager = emf.createEntityManager();
    }

    public void entityManagerClose() {
        entityManager.close();
    }

    public void create(Supplier supplier) {
        log.info("create() - Запись в БД объекта {}", supplier);
        entityManager.getTransaction().begin();
        entityManager.persist(supplier);
        entityManager.getTransaction().commit();
    }

    public Optional<Supplier> read(int id) {
        entityManager.getTransaction().begin();
        Supplier supplier = entityManager.find(Supplier.class, id);
        entityManager.getTransaction().commit();
        log.info("read() - Считано из БД: {}", supplier);
        return Optional.ofNullable(supplier);
    }

    public boolean update(int id) {
        boolean success = false;
        entityManager.getTransaction().begin();
        Supplier supplier = entityManager.find(Supplier.class, id);
        if (Objects.nonNull(supplier)) {
            supplier.setCompanyName(supplier.getCompanyName() + " - Updated");
            success = true;
            log.info("update() - Изменение прошло успешно.");
        } else {
            log.warn("update() - Попытка изменить несуществующую запись!");
        }
        entityManager.getTransaction().commit();
        return success;
    }

    public boolean delete(int id) {
        boolean success = false;
        entityManager.getTransaction().begin();
        Supplier supplier = entityManager.find(Supplier.class, id);
        if (Objects.nonNull(supplier)) {
            entityManager.remove(supplier);
            success = true;
            log.info("delete() - Удаление прошло успешно.");
        } else {
            log.warn("delete() - Попытка удалить несуществующую запись!");
        }
        entityManager.getTransaction().commit();
        return success;
    }
}
