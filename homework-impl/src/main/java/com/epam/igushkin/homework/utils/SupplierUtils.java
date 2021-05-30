package com.epam.igushkin.homework.utils;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Supplier;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class SupplierUtils {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");

    public Supplier create(String companyName, String phone) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        var supplier = new Supplier();
        try {
            transaction.begin();
            supplier.setCompanyName(companyName);
            if (Objects.nonNull(phone)) {
                supplier.setPhone(phone);
            }
            entityManager.persist(supplier);
            transaction.commit();
            log.info("create() - Запись в БД объекта {}", supplier);
        } catch (RuntimeException e) {
            log.error("create() - Запись завершилась неудачно.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return supplier;
    }

    public Optional<Supplier> read(int id) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        Supplier supplier = null;
        try {
            transaction.begin();
            supplier = entityManager.find(Supplier.class, id);
            log.info("read() - Считано из БД: {}", supplier);
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("read() - Ошибка при получении объекта из БД", e);
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(supplier);
    }

    public List<Supplier> readAll() {
        var entityManager = emf.createEntityManager();
        var cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Supplier> cq = cb.createQuery(Supplier.class);
        Root<Supplier> rootEntry = cq.from(Supplier.class);
        CriteriaQuery<Supplier> all = cq.select(rootEntry);
        TypedQuery<Supplier> allQuery = entityManager.createQuery(all);
        var resultList = allQuery.getResultList();
        entityManager.close();
        return resultList;
    }

    public boolean update(int id) {
        var entityManager = emf.createEntityManager();
        var success = false;
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var supplier = entityManager.find(Supplier.class, id);
            if (Objects.nonNull(supplier)) {
                supplier.setCompanyName(" - Updated");
                success = true;
                log.info("update() - Изменение прошло успешно.");
            } else {
                log.warn("update() - Попытка изменить несуществующую запись!");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("update() - Ошибка при обновлении записи в БД.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return success;
    }

    public boolean delete(int id) {
        var entityManager = emf.createEntityManager();
        var success = false;
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var supplier = entityManager.find(Supplier.class, id);
            if (Objects.nonNull(supplier)) {
                entityManager.remove(supplier);
                success = true;
                log.info("delete() - Удаление прошло успешно.");
            } else {
                log.warn("delete() - Попытка удалить несуществующую запись!");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("delete() - Ошибка при удалении записи из БД.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return success;
    }
}
