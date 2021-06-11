package com.epam.igushkin.homework.converters.customer;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DtoToCustomerConverter implements Converter<CustomerDTO, Customer> {
    @Override
    public Customer convert(CustomerDTO customerDTO) {
        var customer = new Customer();
        customer.setCustomerName(customerDTO.getCustomerName())
                .setPhone(customerDTO.getPhone());
        log.info("convert() - dtoToCustomer до {} после {}", customerDTO, customer);
        return customer;
    }
}
