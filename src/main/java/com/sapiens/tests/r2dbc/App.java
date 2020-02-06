package com.sapiens.tests.r2dbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
