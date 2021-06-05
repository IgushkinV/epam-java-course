package com.epam.igushkin.homework.repository.ipml;

import com.epam.igushkin.homework.domain.entity.Supplier;
import com.epam.igushkin.homework.repository.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class SupplierRepository implements Repository<Supplier> {

    private final EntityManagerFactory emf;

    public Optional<Supplier> create(Supplier supplier) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(supplier);
            transaction.commit();
            log.info("create() - Запись в БД объекта {}", supplier);
        } catch (Exception e) {
            log.error("create() - Запись завершилась неудачно.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(supplier);
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

    public Optional<Supplier> update(Supplier supplier) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var tempSupplier = entityManager.find(Supplier.class, supplier.getSupplierId());
            if (Objects.nonNull(tempSupplier)) {
                tempSupplier.setCompanyName(supplier.getCompanyName());
                tempSupplier.setPhone(supplier.getPhone());
                log.info("update() - Изменение прошло успешно.");
            } else {
                log.warn("update() - Попытка изменить несуществующую запись!");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("update() - Ошибка во время изменения записи.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return read(supplier.getSupplierId());
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
