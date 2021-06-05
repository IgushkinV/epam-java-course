package com.epam.igushkin.homework;

import com.epam.igushkin.homework.config.JPAConfig;
import com.epam.igushkin.homework.domain.entity.Product;
import com.epam.igushkin.homework.repository.ipml.CustomerRepository;
import com.epam.igushkin.homework.repository.ipml.OrderRepository;
import com.epam.igushkin.homework.repository.ipml.ProductRepository;
import com.epam.igushkin.homework.repository.ipml.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Set;

@Slf4j
public class Main {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(JPAConfig.class);
        var customerRepository = (CustomerRepository) applicationContext.getBean("customerRepository");
        var customer = customerRepository.read(2).get();
        log.info("{}", customer);
        customer.setCustomerName("After updating");
        customer.setCustomerId(10);
        log.info("{}", customerRepository.update(customer));

        var supplierRepository = (SupplierRepository) applicationContext.getBean("supplierRepository");
        var supplier = supplierRepository.read(3).get();
        log.info("{}", supplier);
        supplier.setCompanyName("After updating");
        supplier.setPhone("00000000");
        supplierRepository.update(supplier);
        log.info("{}", supplierRepository.read(3).get());

        var orderRepository = (OrderRepository) applicationContext.getBean("orderRepository");
        log.info("{}", orderRepository.read(4).get());
        var orderToUpd = orderRepository.read(4).get();
        orderToUpd.setOrderNumber("Num - Upd");
        log.info("{}", orderRepository.update((orderToUpd)));
        orderRepository.delete(1);
        var productRepository = (ProductRepository) applicationContext.getBean("productRepository");
        log.info("{}", productRepository.read(3));
        var productToUpdate = new Product();
        productToUpdate.setProductName("Updated-04062021");
        productToUpdate.setSupplier(supplierRepository.read(1).get());
        var orderSet = Set.of(orderRepository.read(4).get(), orderRepository.read(5).get());
        productToUpdate.setOrders(orderSet);
        productToUpdate.setProductId(2);
        productRepository.update(productToUpdate);
        log.info("After update: {}", productRepository.read(2));
    }
}
