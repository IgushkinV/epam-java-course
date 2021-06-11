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
@ComponentScan("com/epam/igushkin/homework")
public class JPAConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("EntityManagerFactory");
    }
}
