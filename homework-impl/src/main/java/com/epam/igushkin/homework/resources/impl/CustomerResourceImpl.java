package com.epam.igushkin.homework.resources.impl;
import com.epam.igushkin.homework.converters.customer.CustomerToDTOConverter;
import com.epam.igushkin.homework.converters.customer.DtoToCustomerConverter;
import com.epam.igushkin.homework.dto.CustomerDTO;
import com.epam.igushkin.homework.resources.CustomerResource;
import com.epam.igushkin.homework.services.impl.CustomerServiceImpl;
import com.epam.igushkin.homework.validator.CustomerDTOValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализует методы для получения, записи, удаления, передавая в DTO в сервисный слой.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerResourceImpl implements CustomerResource {

    private final CustomerServiceImpl customerService;
    private final CustomerToDTOConverter customerToDTOConverter;
    private final DtoToCustomerConverter dtoToCustomerConverter;

    /**
     * Получает Customer по id из базы.
     *
     * @param id уникальный номер заказчика в базе.
     * @return  CustomerDTO, содержит данные заказчика, считанного из базы.
     */
    @Override
    public CustomerDTO getCustomer(Integer id) {
        log.debug("getCustomer() - id = {}", id);
        var customer = customerService.findById(id);
        log.debug("getCustomer() - Найден кастомер {}", customer);
        return customerToDTOConverter.convert(customer);
    }

    /**
     * Получает всех заказчиков из базы.
     *
     * @return List<Customer>
     */
    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAll().stream()
                .map(customerToDTOConverter::convert)
                .collect(Collectors.toList());
    }

    /**
     * Добавляет заказчика в базу.
     *
     * @param customerDTO содержит данные заказчика, которого нужно добавить.
     * @return CustomerDTO с данными добавленного заказчика.
     */
    @Override
    public CustomerDTO addCustomer(@Valid CustomerDTO customerDTO) {
        var customer = dtoToCustomerConverter.convert(customerDTO);
        var savedCustomerDTO = customerToDTOConverter.convert(customerService.save(customer));
        return savedCustomerDTO;
    }

    /**
     * Обновляет данные заказчика по id.
     *
     * @param id уникальный номер заказчика в базе.
     * @param customerDTO содержит данные для обновления заказчика.
     * @return CustomerDTO с обновленными данными заказчика.
     */
    @Override
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        var customer = customerService.findById(id);
        customer.setCustomerName(customerDTO.getCustomerName())
                .setPhone(customerDTO.getPhone());
        var updatedCustomer = customerService.save(customer);
        log.info("updateCustomer() - {}", updatedCustomer);
        return customerToDTOConverter.convert(updatedCustomer);
    }

    /**
     * Удаляет заказчика из базы по id.
     *
     * @param id уникальный номер заказчика в базе.
     * @return результат удаления (true или false).
     */
    @Override
    public boolean deleteCustomer(Integer id) {
        boolean result = customerService.delete(id);
        log.info("deleteCustomer() - {}", result);
        return result;
    }

    @InitBinder
    protected void initBinder (WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new CustomerDTOValidator());
    }
}
