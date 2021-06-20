package com.epam.igushkin.homework.repository;

import com.epam.igushkin.homework.domain.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с репозиторием и выполения CRUD операций.
 */
@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer> {
}
