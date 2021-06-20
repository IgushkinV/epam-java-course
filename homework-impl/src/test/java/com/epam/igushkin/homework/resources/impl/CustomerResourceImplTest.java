package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.converters.customer.CustomerToDTOConverter;
import com.epam.igushkin.homework.converters.customer.DtoToCustomerConverter;
import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.dto.CustomerDTO;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.resources.CustomerResource;
import com.epam.igushkin.homework.services.CustomerService;
import com.epam.igushkin.homework.services.impl.CustomerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerResource.class)
@ContextConfiguration(classes = {
        CustomerServiceImpl.class,
        CustomerResourceImpl.class,
        CustomerToDTOConverter.class,
        DtoToCustomerConverter.class,
        MyExceptionHandler.class})
public class CustomerResourceImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerToDTOConverter customerToDTOConverter;

    @MockBean
    DtoToCustomerConverter dtoToCustomerConverter;

    @MockBean
    CustomerService customerService;

    @Test
    public void testGetCustomerReturnsRightCustomer() throws Exception {
        Customer customer = getTestCustomer();
        CustomerDTO customerDTO = getTestDTO();
        when(customerService.findById(1)).thenReturn(customer);
        when(customerToDTOConverter.convert(customer)).thenReturn(customerDTO);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customer_name").value("Vasyn"))
                .andReturn();
    }

    @Test
    public void testPostReturnsSameCustomer() throws Exception {
        Customer customer = getTestCustomer();
        CustomerDTO customerDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customerDTO);
        when(customerService.save(customer)).thenReturn(customer);
        when(dtoToCustomerConverter.convert(customerDTO)).thenReturn(customer);
        when(customerToDTOConverter.convert(customer)).thenReturn(customerDTO);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_name").value("Vasyn"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testDeleteReturnsTrue() throws Exception {
        when(customerService.delete(1)).thenReturn(true);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/customer/1"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("true", result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteReturnsFalse() throws Exception {
        when(customerService.delete(1)).thenReturn(false);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/customer/1"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("false", result.getResponse().getContentAsString());
    }

    @Test
    public void testGetCustomerReturnsStatus404() throws Exception {
        when(customerService.findById(1)).thenThrow(new NoEntityFoundException("Выброс из тестов."));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCustomerReturnsUpdatedEntity() throws Exception {
        Customer customer2 = getTestCustomer();
        CustomerDTO customerDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customerDTO);

        when(dtoToCustomerConverter.convert(customerDTO)).thenReturn(customer2);
        when(customerService.update(customer2)).thenReturn(customer2);
        when(customerToDTOConverter.convert(customer2)).thenReturn(customerDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer_name").value("Vasyn"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUpdateCustomerReturns404() throws Exception {
        Customer customer2 = getTestCustomer();
        CustomerDTO customerDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customerDTO);
        when(dtoToCustomerConverter.convert(customerDTO)).thenReturn(customer2);
        when(customerService.update(customer2)).thenThrow(new NoEntityFoundException("Из тестов."));
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.put("/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
        String message = "В базе не существует запись с указанным id.";
        Assertions.assertEquals(message, result.getResponse().getErrorMessage());
    }

    @Test
    public void testPostCustomerReturns400Status() throws Exception {
        CustomerDTO customerDTO = new CustomerDTO()
                .setCustomerId(1)
                //Номер телефона не проходит проверку валидатором. Должен начинаться на +7-.
                .setPhone("+8-1111")
                .setCustomerName("Vasyn");
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(customerDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllCustomersReturnsListOfTwoElement() throws Exception {
        Customer customer = getTestCustomer();
        CustomerDTO customerDTO = getTestDTO();
        List<Customer> testList = List.of(customer, customer);

        when(customerService.getAll()).thenReturn(testList);
        when(customerToDTOConverter.convert(customer)).thenReturn(customerDTO);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/customer/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
       String stringBody = result.getResponse().getContentAsString();
       JSONArray array = new JSONArray(stringBody);

       Assertions.assertEquals(2, array.length());
    }

    private Customer getTestCustomer() {
        return new Customer()
                .setCustomerId(1)
                .setPhone("+7-1111")
                .setCustomerName("Vasyn");
    }

    private CustomerDTO getTestDTO() {
        return new CustomerDTO()
                .setCustomerId(1)
                .setPhone("+7-1111")
                .setCustomerName("Vasyn");
    }
}