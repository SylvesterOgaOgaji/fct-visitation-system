package com.fct.visitation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VisitationApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisitationApplication.class, args);
    }
}
