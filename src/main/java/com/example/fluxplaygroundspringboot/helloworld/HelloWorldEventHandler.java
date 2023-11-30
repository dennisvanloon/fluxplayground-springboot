package com.example.fluxplaygroundspringboot.helloworld;

import io.fluxcapacitor.javaclient.tracking.handling.HandleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
class HelloWorldEventHandler {

    @HandleEvent
    void handle(HelloWorld event) {
        log.info("Hello World!");
    }
}