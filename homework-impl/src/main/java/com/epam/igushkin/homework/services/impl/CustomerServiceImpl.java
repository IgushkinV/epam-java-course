package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.dto.CustomerDTO;
import com.epam.igushkin.homework.exceptions.MyServiceException;
import com.epam.igushkin.homework.repository.ipml.CustomerRepository;
import com.epam.igushkin.homework.services.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements Service<Customer, CustomerDTO> {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerDTO create(CustomerDTO dto) {
        CustomerDTO createdCustomerDTO = null;
        var customer = mapDTOToEntity(dto);
        var createdCustomerOpt = customerRepository.create(customer);
        if (createdCustomerOpt.isPresent()) {
            createdCustomerDTO = mapEntityToDTO(createdCustomerOpt.get());
            log.debug("create() - Считан из репозитория: {}", createdCustomerDTO);
        } else {
            throw new MyServiceException("Получение null при попытке считать записанный ранее объект.");
        }
        return createdCustomerDTO;
    }

    @Override
    public CustomerDTO read(int id) {
        var readCustomerOpt = customerRepository.read(id);
        if (readCustomerOpt.isPresent()) {
            return mapEntityToDTO(readCustomerOpt.get());
        } else {
            throw new MyServiceException("Получение null при попытке считать из репозитория.");
        }
    }

    @Override
    public List<CustomerDTO> readAll() {
        return customerRepository.readAll().stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO update(CustomerDTO dto) {
        var updatedCustomer = customerRepository.update(mapDTOToEntity(dto));
        if (updatedCustomer.isPresent()) {
            return mapEntityToDTO(updatedCustomer.get());
        } else {
            throw new MyServiceException("Получение null при чтении обновленного объекта из репозитория.");
        }
    }

    @Override
    public boolean delete(int id) {
        return customerRepository.delete(id);
    }

    @Override
    public Customer mapDTOToEntity(CustomerDTO dto) {
        var customer = new Customer();
        customer.setCustomerName(dto.getCustomerName());
        if (Objects.nonNull(dto.getPhone())) {
            customer.setPhone(dto.getPhone());
        }
        return customer;
    }

    @Override
    public CustomerDTO mapEntityToDTO(Customer entity) {
        return CustomerDTO.builder()
                .customerId(entity.getCustomerId())
                .customerName(entity.getCustomerName())
                .phone(entity.getPhone())
                .build();
    }
}
