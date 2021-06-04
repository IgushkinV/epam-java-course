package com.epam.igushkin.homework.repository.ipml;

import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.repository.IRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductRepository implements IRepository<Product> {

    private final EntityManagerFactory emf;
    private final SupplierRepository supplierRepository;

    public Product create(Product product) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(product);
            transaction.commit();
            log.info("create() - Запись в БД объекта {}", product);
        } catch (Exception e) {
            log.error("create() - Ошибка при создании и записи нового объекта.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        var createdProduct = entityManager.find(Product.class, product.getProductId());
        log.debug("create() - Объект {} был записан в БД", createdProduct);
        return createdProduct;
    }

    public Optional<Product> read(int id) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        ;
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

    public boolean update(Product updatedProduct) {
        var entityManager = emf.createEntityManager();
        var success = false;
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var oldProduct = entityManager.find(Product.class, updatedProduct.getProductId());
            if (Objects.nonNull(oldProduct)) {
                oldProduct.setProductName(updatedProduct.getProductName());
                oldProduct.setUnitPrice(updatedProduct.getUnitPrice());
                oldProduct.setSupplier(oldProduct.getSupplier());
                oldProduct.setDiscontinued(updatedProduct.isDiscontinued());
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

    public boolean delete(int id) {
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
