package com.epam.igushkin.homework.utils;

import com.epam.igushkin.homework.domain.entity.Order;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class OrderUtils {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");

    public void create(Order order) {
        EntityManager entityManager = emf.createEntityManager();
        log.info("create() - Запись в БД объекта {}", order);
        entityManager.getTransaction().begin();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Optional<Order> read(int id) {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Order order = entityManager.find(Order.class, id);
        entityManager.getTransaction().commit();
        log.info("read() - Считано из БД: {}", order);
        entityManager.close();
        return Optional.ofNullable(order);
    }

    public boolean update (int id) {
        EntityManager entityManager = emf.createEntityManager();
        boolean success = false;
        entityManager.getTransaction().begin();
        Order order = entityManager.find(Order.class, id);
        if (Objects.nonNull(order)) {
            order.setOrderDate(LocalDateTime.of(3000, 1, 1, 0, 0, 0));
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
        Order order = entityManager.find(Order.class, id);
        if (Objects.nonNull(order)) {
            entityManager.remove(order);
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
