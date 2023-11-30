package com.example.fluxplaygroundspringboot.config;

import io.fluxcapacitor.javaclient.configuration.spring.FluxCapacitorSpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(FluxCapacitorSpringConfig.class)
@ComponentScan
public class SpringConfig {
}
