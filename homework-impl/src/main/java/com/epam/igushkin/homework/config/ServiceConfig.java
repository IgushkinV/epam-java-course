package com.epam.igushkin.homework.config;

import com.epam.igushkin.homework.services.impl.CustomerCRUDServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(JPAConfig.class)
public class ServiceConfig {
}
