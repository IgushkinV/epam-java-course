package com.epam.igushkin.homework.converters.customer;

import com.epam.igushkin.homework.dto.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StringToCustomerDTOConverter implements Converter<String, CustomerDTO> {
    @Override
    public CustomerDTO convert(String s) {
        String[] strings = s.split(",");
        var customerDto = CustomerDTO.builder()
                .customerName(strings[0].split(":")[1])
                .phone(strings[1].split(":")[1])
                .build();
        log.info("convert() - convert from '{}' to {}", s, customerDto);
        return customerDto;
    }
}
