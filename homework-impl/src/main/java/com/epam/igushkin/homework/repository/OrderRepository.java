package com.epam.igushkin.homework.repository;

import com.epam.igushkin.homework.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /*//todo проверить, как правильно указать имя таблицы
    @Query("select o from [order] o where o.customerId = :customerId")
    List<Order> findAllByCustomerId (@Param("customerId") Long customerId);*/
}
