package com.epam.igushkin.homework.repository;
import com.epam.igushkin.homework.domain.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с репозиторием и выполения CRUD операций.
 */
@Repository
public interface CustomerRepository extends CrudRepository <Customer, Integer> {


}
