package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.CustomerRepository;
import com.epam.igushkin.homework.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Override
    public Customer findById(Integer id) {
        var customerOpt = customerRepository.findById(id);
        log.debug("findById() - Найден заказчик {}", customerOpt);
        return customerOpt.orElseThrow(() -> new NoEntityFoundException("Заказчик с id" + id + "не найден."));
    }

    @Override
    public Customer update(Integer id, Customer customer) {
        var oldCustomerOpt = customerRepository.findById(id);
        if (oldCustomerOpt.isEmpty()) {
            throw new NoEntityFoundException("Поставщик с id" + id + "не найден.");
        }
        var oldCustomer = oldCustomerOpt.get();
        oldCustomer.setCustomerName(customer.getCustomerName())
                .setPhone(customer.getPhone());
        return customerRepository.save(oldCustomer);
    }

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
