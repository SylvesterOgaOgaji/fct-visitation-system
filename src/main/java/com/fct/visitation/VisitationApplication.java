package com.fct.visitation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.fct.visitation.models") // Scan entities
@EnableJpaRepositories("com.fct.visitation.repositories") // Scan repositories
public class VisitationApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisitationApplication.class, args);
    }
}
