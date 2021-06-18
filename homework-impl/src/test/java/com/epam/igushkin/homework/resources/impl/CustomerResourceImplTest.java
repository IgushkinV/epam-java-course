package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.resources.CustomerResource;
import com.epam.igushkin.homework.services.impl.CustomerServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CustomerResource.class)
@ExtendWith(SpringExtension.class)
class CustomerResourceImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerServiceImpl customerService;


}