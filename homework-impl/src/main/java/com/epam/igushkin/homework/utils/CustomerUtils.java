package com.epam.igushkin.homework.utils;

import com.epam.igushkin.homework.domain.entity.Customer;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class CustomerUtils {

    private final EntityManager entityManager;

    public CustomerUtils() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EntityManager");
        entityManager = emf.createEntityManager();
    }

    public void entityManagerClose() {
        entityManager.close();
    }

    public void create(Customer customer) {
        log.info("create() - Запись в БД объекта {}", customer);
        entityManager.getTransaction().begin();
        entityManager.persist(customer);
        entityManager.getTransaction().commit();
    }

    public Optional<Customer> read(int id) {
        entityManager.getTransaction().begin();
        Customer customer = entityManager.find(Customer.class, id);
        entityManager.getTransaction().commit();
        log.info("read() - Считано из БД: {}", customer);
        return Optional.ofNullable(customer);
    }

    public boolean update(int id) {
        boolean success = false;
        entityManager.getTransaction().begin();
        Customer customer = entityManager.find(Customer.class, id);
        if (Objects.nonNull(customer)) {
            customer.setCustomerName(" - Updated");
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
        Customer customer = entityManager.find(Customer.class, id);
        if (Objects.nonNull(customer)) {
            entityManager.remove(customer);
            success = true;
            log.info("delete() - Удаление прошло успешно.");
        } else {
            log.warn("delete() - Попытка удалить несуществующую запись!");
        }
        entityManager.getTransaction().commit();
        return success;
    }
}
