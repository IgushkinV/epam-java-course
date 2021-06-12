package com.epam.igushkin.homework.repository;

import com.epam.igushkin.homework.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с репозиторием и выполения CRUD операций.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /*//todo проверить, как правильно указать имя таблицы
    @Query("select o from [order] o where o.customerId = :customerId")
    List<Order> findAllByCustomerId (@Param("customerId") Long customerId);*/
}
