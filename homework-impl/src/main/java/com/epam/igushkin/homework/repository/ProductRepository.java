package com.epam.igushkin.homework.repository;

import com.epam.igushkin.homework.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с репозиторием и выполения CRUD операций.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
