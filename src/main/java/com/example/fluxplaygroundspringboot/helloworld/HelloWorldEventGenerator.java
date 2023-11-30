package com.example.fluxplaygroundspringboot.helloworld;

import io.fluxcapacitor.javaclient.FluxCapacitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloWorldEventGenerator {

    private final FluxCapacitor fluxCapacitor;

    public HelloWorldEventGenerator(FluxCapacitor fluxCapacitor) {
        this.fluxCapacitor = fluxCapacitor;
    }

    @Scheduled(fixedDelay = 5000)
    public void generateEvent() {
        fluxCapacitor.eventGateway().publish(new HelloWorld());
    }
}
