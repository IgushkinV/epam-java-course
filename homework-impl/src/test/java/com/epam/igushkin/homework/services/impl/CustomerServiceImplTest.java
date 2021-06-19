package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.CustomerRepository;
import com.epam.igushkin.homework.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CustomerServiceImplTest {

    @Autowired
    private CustomerService customerService;

    @TestConfiguration
    static class CustomerServiceTestConfiguration {
        @Bean
        CustomerService customerService(CustomerRepository repository) {
            return new CustomerServiceImpl(repository);
        }
    }

    @Test
    public void testFindByIdReturnsRightCustomer() {
        var customers = getCustomers();
        var testCustomer = customers.get(0);
        customerService.save(testCustomer);
        var savedCustomer = customerService.findById(1);
        assertEquals(testCustomer.getCustomerName(), savedCustomer.getCustomerName());
    }

    @Test
    public void testFindByIdThrowsException() {
        assertThrows(NoEntityFoundException.class, () -> customerService.findById(0));
    }

    @Test
    public void testDeleteReturnsTrue() {
        var customers = getCustomers();
        var savedCustomer = customerService.save(customers.get(0));
        assertTrue(customerService.delete(savedCustomer.getCustomerId()));
    }

    @Test
    public void testDeleteReturnsFalse() {
        assertFalse(customerService.delete(0));
    }

    @Test
    public void testGetAllReturnsListOfTwoCustomers() {
        var expected = 2;
        var customers = getCustomers();
        customerService.save(customers.get(0));
        customerService.save(customers.get(1));

        var returnedCustomers = customerService.getAll();
        var actual = returnedCustomers.size();

        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateChangesCustomerName() {
        var customers = getCustomers();
        var savedCustomer = customerService.save(customers.get(0));
        savedCustomer.setCustomerId(savedCustomer.getCustomerId())
                .setCustomerName("Changed")
                .setPhone(savedCustomer.getPhone());

        var updatedCustomer = customerService.update(savedCustomer);

        assertNotEquals(getCustomers().get(0).getCustomerName(), updatedCustomer.getCustomerName());
        assertEquals(savedCustomer.getCustomerName(), updatedCustomer.getCustomerName());
    }

    /**
     * Создает два экзепляра Customer для тестов.
     *
     * @return List<Customer>
     */
    private List<Customer> getCustomers() {
        Customer customer = new Customer()
                .setCustomerName("TasteName")
                .setPhone("+7-5555-4444");
        Customer customer2 = new Customer()
                .setCustomerName("TasteName2")
                .setPhone("+7-5555-44442");
        return List.of(customer, customer2);
    }
}