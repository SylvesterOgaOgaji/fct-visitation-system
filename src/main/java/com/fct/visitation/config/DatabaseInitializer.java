package com.fct.visitation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Profile("dev") // Only run in development environment
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Create schema if not exists
            ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
            schemaPopulator.addScript(new ClassPathResource("db/schema.sql"));
            schemaPopulator.execute(dataSource);

            // Initialize data if not exists
            ResourceDatabasePopulator dataPopulator = new ResourceDatabasePopulator();
            dataPopulator.addScript(new ClassPathResource("db/data.sql"));
            dataPopulator.execute(dataSource);

            System.out.println("Database initialization completed successfully!");
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
        }
    }
}