package com.epam.igushkin.homework.repository.ipml;

import com.epam.igushkin.homework.domain.entity.Customer;
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
@Component
@RequiredArgsConstructor
public class CustomerRepository implements Repository<Customer> {

    private final EntityManagerFactory emf;

    public Optional<Customer> create(Customer customer) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(customer);
            transaction.commit();
            log.info("create() - Запись в БД объекта {}", customer);
        } catch (Exception e) {
            log.error("create() - Запись завершилась неудачно.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return read(customer.getCustomerId());
    }

    public Optional<Customer> read(int id) {
        var entityManager = emf.createEntityManager();
        Customer customer = null;
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            customer = entityManager.find(Customer.class, id);
            transaction.commit();
            log.info("read() - Считано из БД: {}", customer);
        } catch (RuntimeException e) {
            log.error("read() - Ошибка при чтении из БД.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(customer);
    }

    public List<Customer> readAll() {
        var entityManager = emf.createEntityManager();
        var cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> rootEntry = cq.from(Customer.class);
        CriteriaQuery<Customer> all = cq.select(rootEntry);
        TypedQuery<Customer> allQuery = entityManager.createQuery(all);
        var customerList = allQuery.getResultList();
        entityManager.close();
        return customerList;
    }

    public Optional<Customer> update(Customer customer) {
        var entityManager = emf.createEntityManager();
        var transaction = entityManager.getTransaction();
        Customer updatedCustomer = null;
        try {
            transaction.begin();
            var tempCustomer = entityManager.find(Customer.class, customer.getCustomerId());
            if (Objects.nonNull(tempCustomer)) {
                tempCustomer.setCustomerName(customer.getCustomerName());
                tempCustomer.setPhone(customer.getPhone());
                log.info("update() - Изменение прошло успешно.");
            } else {
                log.warn("update() - Попытка изменить несуществующую запись!");
            }
            updatedCustomer = entityManager.find(Customer.class, customer.getCustomerId());
            transaction.commit();
        } catch (RuntimeException e) {
            log.error("update() - Ошибка во время изменения записи.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return Optional.ofNullable(updatedCustomer);
    }

    public boolean delete(int id) {
        var entityManager = emf.createEntityManager();
        boolean success = false;
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var customer = entityManager.find(Customer.class, id);
            if (Objects.nonNull(customer)) {
                entityManager.remove(customer);
                success = true;
                log.info("delete() - Удаление прошло успешно.");
            } else {
                log.warn("delete() - Попытка удалить несуществующую запись!");
            }
            transaction.commit();
        } catch (Exception e) {
            log.error("delete() - Ошибка во время удаления.", e);
            transaction.rollback();
        } finally {
            entityManager.close();
        }
        return success;
    }
}
