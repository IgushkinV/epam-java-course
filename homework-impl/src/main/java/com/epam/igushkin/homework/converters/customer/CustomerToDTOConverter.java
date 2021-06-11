package com.epam.igushkin.homework.converters.customer;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerToDTOConverter implements Converter<Customer, CustomerDTO> {
    @Override
    public CustomerDTO convert(Customer customer) {
        log.debug("convert() - Получен Customer на вход: {}", customer);
        CustomerDTO.builder();
        return CustomerDTO.builder()
                .customerName(customer.getCustomerName())
                .phone(customer.getPhone())
                .build();
    }
}
