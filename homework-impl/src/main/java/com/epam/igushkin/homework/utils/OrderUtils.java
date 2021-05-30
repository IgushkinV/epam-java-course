package com.epam.igushkin.homework.utils;

import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Product;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class OrderUtils {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");

    public Order create(String orderNumber, int customerId, LocalDateTime orderDate, BigDecimal totalAmount,
                        List<Integer> productIds) {
        var entityManager = emf.createEntityManager();
        var order = new Order();
        var customerMadeOrder = new CustomerUtils().read(customerId).get();
        order.setOrderNumber(orderNumber);
        order.setCustomer(customerMadeOrder);
        order.setOrderDate(orderDate);
        order.setTotalAmount(totalAmount);
        Set<Product> productSet = new HashSet<>();
        for (int num : productIds) {
            productSet.add(new ProductUtils().read(num).get());
        }
        order.setProducts(productSet);
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(order);
            transaction.commit();
            log.info("create() - Запись в БД объекта {}", order);
        } catch (RuntimeException e) {
            log.error("create() - Ошибка при записи нового заказа в БД.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return order;
    }

    public Optional<Order> read(int id) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        Order order = null;
        try {
            transaction.begin();
            order = entityManager.find(Order.class, id);
            transaction.commit();
            log.info("read() - Считано из БД: {}", order);
        } catch (RuntimeException e) {
            log.error("read() - Ошибка при чтении из БД.", e);
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(order);
    }

    public List<Order> readAll() {
        var entityManager = emf.createEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> rootEntry = cq.from(Order.class);
        CriteriaQuery<Order> all = cq.select(rootEntry);
        TypedQuery<Order> allQuery = entityManager.createQuery(all);
        var resultList = allQuery.getResultList();
        entityManager.close();
        return resultList;
    }

    public boolean update (int id) {
        var entityManager = emf.createEntityManager();
        var success = false;
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var order = entityManager.find(Order.class, id);
            if (Objects.nonNull(order)) {
                order.setOrderDate(LocalDateTime.of(3000, 1, 1, 0, 0, 0));
                success = true;
                log.info("update() - Изменение прошло успешно.");
            } else {
                log.warn("update() - Попытка изменить несуществующую запись!");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("update() -  Ошибка при обновлении записи.", e);
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
            var order = entityManager.find(Order.class, id);
            if (Objects.nonNull(order)) {
                entityManager.remove(order);
                success = true;
                log.info("delete() - Удаление прошло успешно");
            } else {
                log.warn("delete() - Попытка удалить несуществующую запись!");
            }
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("delete() - Ошибка при удалении", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return success;
    }
}
