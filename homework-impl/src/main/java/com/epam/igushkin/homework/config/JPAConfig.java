package com.epam.igushkin.homework.config;

import com.epam.igushkin.homework.repository.ipml.CustomerRepository;
import com.epam.igushkin.homework.repository.ipml.OrderRepository;
import com.epam.igushkin.homework.repository.ipml.ProductRepository;
import com.epam.igushkin.homework.repository.ipml.SupplierRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
@ComponentScan("src/main/java/com/epam/igushkin/homework")
public class JPAConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("EntityManagerFactory");
    }

    @Bean
    public CustomerRepository customerRepository() {
        return new CustomerRepository(entityManagerFactory());
    }

    @Bean
    public SupplierRepository supplierRepository() {
        return new SupplierRepository(entityManagerFactory());
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository(entityManagerFactory(), supplierRepository());
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository(entityManagerFactory(), customerRepository(), productRepository());
    }
}
