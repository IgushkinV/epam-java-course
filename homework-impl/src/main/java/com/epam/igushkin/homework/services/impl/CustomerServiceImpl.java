package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.logger.Logging;
import com.epam.igushkin.homework.repository.CustomerRepository;
import com.epam.igushkin.homework.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * Содержит реализацию методов для работы с репозиторием CustomerRepository.
 */

@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    //private final MessageSource errorSource;

    /**
     * Сохраняет заказчика в репозиторий.
     *
     * @param customer
     * @return сохраненного заказчика.
     */
    @Logging
    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Считывает всех заказчиков из репозитория.
     *
     * @return List<Customer>
     */
    @Override
    public List<Customer> getAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    /**
     * Находит всех заказчика в репозитории по id.
     *
     * @param id уникальный номер заказчика в репозитории.
     * @return Customer
     */
    @Override
    public Customer findById(Integer id) {
        var customerOpt = customerRepository.findById(id);
        log.debug("findById() - Найден заказчик {}", customerOpt);
        Object[] args = {id};
        String message = "Запись не найдена.";
        //String message = errorSource.getMessage("noCustomer", args, Locale.getDefault());
        return customerOpt.orElseThrow(() -> new NoEntityFoundException(message));
    }

    /**
     * Обновляет данные заказчика в репозитории.
     *
     * @param customer данные заказчика, которые нужно обновить.
     * @return Customer
     */
    @Override
    public Customer update(Customer customer) {
        var id = customer.getCustomerId();
        var oldCustomerOpt = customerRepository.findById(id);
        if (oldCustomerOpt.isEmpty()) {
            throw new NoEntityFoundException("Поставщик с id" + id + "не найден.");
        }
        var oldCustomer = oldCustomerOpt.get();
        oldCustomer.setCustomerName(customer.getCustomerName())
                .setPhone(customer.getPhone());
        return customerRepository.save(oldCustomer);
    }

    /**
     * Удаляет заказчика из репозирория по его id.
     *
     * @param id уникальный номер заказчика в репозитории.
     * @return результат удаления (true или false).
     */
    @Override
    public boolean delete(Integer id) {
        boolean success = false;
        if (customerRepository.existsById(id)) {
            customerRepository.delete(customerRepository.findById(id).get());
            success = true;
            log.info("delete() - Удаление прошло успешно.");
        } else {
            log.info("delete() - Нечего удалить.");
        }
        return success;
    }
}
