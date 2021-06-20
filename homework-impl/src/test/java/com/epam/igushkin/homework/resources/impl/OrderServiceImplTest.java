package com.epam.igushkin.homework.resources.impl;

import com.epam.igushkin.homework.converters.order.DtoToOrderConverter;
import com.epam.igushkin.homework.converters.order.OrderToDTOConverter;
import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.dto.OrderDTO;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.resources.OrderResource;
import com.epam.igushkin.homework.services.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderResource.class)
@ContextConfiguration(classes = {
        OrderServiceImpl.class,
        OrderResourceImpl.class,
        OrderToDTOConverter.class,
        DtoToOrderConverter.class,
        MyExceptionHandler.class
})
public class OrderServiceImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DtoToOrderConverter dtoToOrderConverter;
    @MockBean
    OrderToDTOConverter orderToDTOConverter;
    @MockBean
    OrderServiceImpl orderService;

    private static final LocalDateTime now = LocalDateTime.now();

    @Test
    public void testGetReturnsOrder() throws Exception {
        Order order = getTestOrder();
        OrderDTO orderDTO = getTestDTO();
        when(orderService.findById(1)).thenReturn(order);
        when(orderToDTOConverter.convert(order)).thenReturn(orderDTO);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/order/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.order_number").value("3-33-333"))
                .andReturn();
    }

    @Test
    public void testGetOrderReturnsStatus404() throws Exception {
        when(orderService.findById(1)).thenThrow(new NoEntityFoundException("Из тестов: нет такого заказа."));
        mockMvc.perform(
                MockMvcRequestBuilders.get("/order/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllOrdersReturnsListOfTwoOrders() throws Exception {
        Order order = getTestOrder();
        OrderDTO orderDTO = getTestDTO();
        List<Order> orderList = List.of(order, order);

        when(orderService.getAll()).thenReturn(orderList);
        when(orderToDTOConverter.convert(order)).thenReturn(orderDTO);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/order/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String stringResponse = result.getResponse().getContentAsString();
        JSONArray array = new JSONArray(stringResponse);

        Assertions.assertEquals(2, array.length());
    }

    @Test
    public void testPostReturnsSameOrder() throws Exception {
        Order order = getTestOrder();
        OrderDTO orderDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(orderDTO);
        when(orderService.save(order)).thenReturn(order);
        when(dtoToOrderConverter.convert(orderDTO)).thenReturn(order);
        when(orderToDTOConverter.convert(order)).thenReturn(orderDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.post(("/order"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order_number").value("3-33-333"));
    }

    @Test
    public void testDeleteReturnsTrue() throws Exception {
        when(orderService.delete(1)).thenReturn(true);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/order/1"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("true", result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteReturnsFalse() throws Exception {
        when(orderService.delete(1)).thenReturn(false);
        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.delete("/order/1"))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertEquals("false", result.getResponse().getContentAsString());
    }

    @Test
    public void testUpdateOrderReturnsPassedOrder() throws Exception {
        Order order = getTestOrder();
        OrderDTO orderDTO = getTestDTO();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(orderDTO);
        when(dtoToOrderConverter.convert(orderDTO)).thenReturn(order);
        when(orderService.update(order)).thenReturn(order);
        when((orderToDTOConverter.convert(order))).thenReturn(orderDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.order_number").value(order.getOrderNumber()));
    }

    @Test
    public void testUpdateOrderReturns404Status() throws Exception {
        OrderDTO orderDTO = getTestDTO();
        Order order = getTestOrder();
        when(dtoToOrderConverter.convert(orderDTO)).thenReturn(order);
        when(orderService.update(order)).thenThrow(new NoEntityFoundException("Из тестов: нет такого заказа."));
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(orderDTO);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    private Customer getTestCustomer() {
        return new Customer()
                .setCustomerId(1)
                .setCustomerName("OrderMaker")
                .setPhone("+7-3333");
    }

    private Order getTestOrder() {
        return new Order()
                .setOrderId(1)
                .setCustomer(getTestCustomer())
                .setOrderNumber("3-33-333")
                .setOrderDate(now)
                .setTotalAmount(BigDecimal.valueOf(150));
    }

    private OrderDTO getTestDTO() {
        return new OrderDTO()
                .setOrderId(1)
                .setCustomerId(1)
                .setOrderNumber("3-33-333")
                .setOrderDate(now.toString())
                .setTotalAmount(BigDecimal.valueOf(150));
    }
}
