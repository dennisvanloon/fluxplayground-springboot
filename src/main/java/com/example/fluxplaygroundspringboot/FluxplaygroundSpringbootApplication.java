package com.example.fluxplaygroundspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FluxplaygroundSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(FluxplaygroundSpringbootApplication.class, args);
    }

}
