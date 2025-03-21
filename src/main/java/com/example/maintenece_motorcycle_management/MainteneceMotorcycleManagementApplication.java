package com.example.maintenece_motorcycle_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.controller", "com.example.controller.admin", "com.example.service",
        "com.example.service_impl",
        "com.example.dao" })
@EntityScan(basePackages = "com.example.model")
@EnableJpaRepositories(basePackages = "com.example.dao")
public class MainteneceMotorcycleManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainteneceMotorcycleManagementApplication.class, args);
    }

}
