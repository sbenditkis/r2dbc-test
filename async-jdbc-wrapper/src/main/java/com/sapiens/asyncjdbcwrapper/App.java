package com.sapiens.asyncjdbcwrapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class App {

    public static void main(String[] args) throws Exception {
        Class.forName("org.postgresql.Driver");
        SpringApplication.run(App.class, args);
    }
}
