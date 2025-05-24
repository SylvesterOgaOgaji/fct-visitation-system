package com.fct.visitation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = {
        "com.fct.visitation",
        "com.fct.common"  // If you have shared components in other packages
    }
)
@EntityScan("com.fct.visitation.models.entity")  // More precise if using entity subpackage
@EnableJpaRepositories(basePackages = "com.fct.visitation.repositories")
public class VisitationApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisitationApplication.class, args);
    }
}