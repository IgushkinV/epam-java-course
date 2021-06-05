package com.epam.igushkin.homework.repository.ipml;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.repository.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRepository implements Repository<Order> {

    private final EntityManagerFactory emf;
    private final Repository<Customer> customerRepository;
    private final Repository<Product> productRepository;


    public Optional<Order> create(Order order) {
        var entityManager = emf.createEntityManager();
        var customerMadeOrder = order.getCustomer();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(order);
            entityManager.persist(customerMadeOrder);
            transaction.commit();
            log.info("create() - Запись в БД объекта {}", order);
        } catch (RuntimeException e) {
            log.error("create() - Ошибка при записи нового заказа в БД.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return read(order.getOrderId());
    }

    public Optional<Order> read(int id) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        Order order = null;
        try {
            transaction.begin();
            order = entityManager.find(Order.class, id);
            transaction.commit();
            entityManager.merge(order);
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

    public Optional<Order> update(Order changedOrder) {
        var entityManager = emf.createEntityManager();
        Order updatedOrder = null;
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var oldOrder = entityManager.find(Order.class, changedOrder.getOrderId());
            if (Objects.nonNull(oldOrder)) {
                oldOrder.setOrderDate(LocalDateTime.of(3000, 1, 1, 0, 0, 0));
                oldOrder.setCustomer(changedOrder.getCustomer());
                oldOrder.setTotalAmount(changedOrder.getTotalAmount());
                oldOrder.setOrderNumber(changedOrder.getOrderNumber());
                updatedOrder = entityManager.find(Order.class, changedOrder.getOrderId());
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
        return Optional.ofNullable(updatedOrder);
    }

    public boolean delete(int id) {
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
