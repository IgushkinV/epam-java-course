package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.converters.customer.CustomerToDTOConverter;
import com.epam.igushkin.homework.converters.customer.DtoToCustomerConverter;
import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.resources.CustomerResource;
import com.epam.igushkin.homework.services.CustomerService;
import com.epam.igushkin.homework.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerResource.class)
@ContextConfiguration(classes = {CustomerServiceImpl.class, CustomerResourceImpl.class,
        CustomerToDTOConverter.class, DtoToCustomerConverter.class})
class CustomerResourceImplTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerToDTOConverter customerToDTOConverter;

    @Autowired
    DtoToCustomerConverter dtoToCustomerConverter;

    @MockBean
    CustomerService customerService;


    @Test
    public void test() throws Exception {
        Customer customer = new Customer()
                .setCustomerId(1)
                .setPhone("+1-1111")
                .setCustomerName("Vasyn");

        when(customerService.findById(1)).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/customer/1"))
                .andExpect(status().isOk());

    }


}