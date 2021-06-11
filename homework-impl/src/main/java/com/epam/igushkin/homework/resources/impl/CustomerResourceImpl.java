package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.converters.customer.CustomerToDTOConverter;
import com.epam.igushkin.homework.converters.customer.DtoToCustomerConverter;
import com.epam.igushkin.homework.converters.customer.StringToCustomerDTOConverter;
import com.epam.igushkin.homework.dto.CustomerDTO;
import com.epam.igushkin.homework.resources.CustomerResource;
import com.epam.igushkin.homework.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CustomerResourceImpl implements CustomerResource {

    private final CustomerService customerService;
    private final CustomerToDTOConverter customerToDTOConverter;
    private final DtoToCustomerConverter dtoToCustomerConverter;
    private final StringToCustomerDTOConverter converter;

    @Override
    public CustomerDTO getCustomer(Integer id) {
        log.debug("getCustomer() - id = {}", id);
        var customer = customerService.findById(id);
        log.debug("getCustomer() - Найден кастомер {}", customer);
        return customerToDTOConverter.convert(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        return customerService.getAll().stream()
                .map(customerToDTOConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO addCustomer(CustomerDTO customerDTO) {
        //var customerDTO = converter.convert(customerDTO);
        var customer = dtoToCustomerConverter.convert(customerDTO);
        var savedCustomerDTO = customerToDTOConverter.convert(customerService.save(customer));
        return savedCustomerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(Integer id, CustomerDTO customerDTO) {
        var customer = customerService.findById(id);
        customer.setCustomerName(customerDTO.getCustomerName())
                .setPhone(customerDTO.getPhone());
        var updatedCustomer = customerService.save(customer);
        log.info("updateCustomer() - {}", updatedCustomer);
        return customerToDTOConverter.convert(updatedCustomer);
    }

    @Override
    public boolean deleteCustomer(Integer id) {
        boolean result = customerService.delete(id);
        log.info("deleteCustomer() - {}", result);
        return result;
    }
}
