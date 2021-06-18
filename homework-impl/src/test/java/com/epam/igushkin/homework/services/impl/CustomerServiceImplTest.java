package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.security.MessageDigest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@RequiredArgsConstructor
public class CustomerServiceImplTest {

    private final CustomerServiceImpl customerService;
    private final MessageSource errorSource;

    @Test
    public void testGetCustomer() {
        Customer customer = new Customer()
                .setCustomerId(1)
                .setCustomerName("TasteName")
                .setPhone("+7-5555-4444");
        customerService.save(customer);
        Customer savedCustomer = customerService.findById(1);
                assertEquals(customer.getCustomerName(), savedCustomer.getCustomerName());

    }

}