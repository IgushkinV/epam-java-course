package com.epam.igushkin.homework.services.impl;

import com.epam.igushkin.homework.domain.entity.Customer;
import com.epam.igushkin.homework.domain.entity.Order;
import com.epam.igushkin.homework.exceptions.NoEntityFoundException;
import com.epam.igushkin.homework.repository.OrderRepository;
import com.epam.igushkin.homework.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderService;

    @TestConfiguration
    static class OrderServiceTestConfiguration {

        @Bean
        OrderService orderService(OrderRepository repository) {
            return new OrderServiceImpl(repository);
        }
    }

    @Test
    public void testFindByIdReturnsRightOrder() {
        var orders = getOrders();
        var testOrder = orders.get(0);
        orderService.save(testOrder);
        var savedOrder = orderService.findById(1);
        assertEquals(testOrder.getOrderNumber(), savedOrder.getOrderNumber());
    }

    @Test
    public void testFindByIdThrowsException() {
        assertThrows(NoEntityFoundException.class, () -> orderService.findById(4));
    }
    @Test
    public void testUpdateChangesOrderNumberAndDate() {
        var order = getOrders().get(0);
        var savedOrder = orderService.save(order);

        var anotherOrder = new Order()
                .setOrderId(savedOrder.getOrderId())
                .setOrderDate(LocalDateTime.now().plusDays(2))
                .setCustomer(savedOrder.getCustomer())
                .setOrderNumber("222-Upd")
                .setTotalAmount(savedOrder.getTotalAmount());

        var updatedOrder = orderService.update(anotherOrder);
        assertNotEquals(order.getOrderNumber(), updatedOrder.getOrderNumber());
        assertNotEquals(order.getOrderDate(), updatedOrder.getOrderDate());
        assertEquals(anotherOrder.getOrderId(), updatedOrder.getOrderId());
        assertEquals(anotherOrder.getCustomer(), updatedOrder.getCustomer());
    }

    @Test
    public void testGetAllReturnsListOfTwoOrders() {
        var expected = 2;
        var orders = getOrders();
        orderService.save(orders.get(0));
        orderService.save(orders.get(1));

        var returnedOrders = orderService.getAll();
        var actual = returnedOrders.size();

        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteReturnsTrue() {
        var orders = getOrders();
        var savedOrder = orderService.save(orders.get(0));
        assertTrue(orderService.delete(savedOrder.getOrderId()));
    }

    @Test
    public void testDeleteReturnsFalse() {
        assertFalse(orderService.delete(1));
    }



    /**
     * Создает два экзепляра Order для тестов.
     *
     * @return List<Order>
     */
    private List<Order> getOrders() {
        Customer customer = new Customer()
                .setCustomerId(1)
                .setCustomerName("TasteName")
                .setPhone("+7-5555-4444");
        Customer customer2 = new Customer()
                .setCustomerId(2)
                .setCustomerName("TasteName2")
                .setPhone("+7-5555-44442");
        Order order1 = new Order()
                .setOrderId(1)
                .setOrderNumber("1-1111")
                .setOrderDate(LocalDateTime.now())
                .setTotalAmount(BigDecimal.valueOf(15))
                .setCustomer(customer);
        Order order2 = new Order()
                .setOrderId(2)
                .setOrderNumber("2-2222")
                .setOrderDate(LocalDateTime.now())
                .setTotalAmount(BigDecimal.valueOf(18))
                .setCustomer(customer2);
        return List.of(order1, order2);
    }
}
