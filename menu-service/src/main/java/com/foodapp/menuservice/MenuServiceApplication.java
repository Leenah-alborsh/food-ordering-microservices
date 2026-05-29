package com.foodapp.menuservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Menu Service application.
 */
@SpringBootApplication
public class MenuServiceApplication {

    /**
     * Starts the Spring Boot Menu Service application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MenuServiceApplication.class, args);
    }
}