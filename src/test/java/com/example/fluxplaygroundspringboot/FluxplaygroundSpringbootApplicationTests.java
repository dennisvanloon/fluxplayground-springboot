package com.example.fluxplaygroundspringboot;

import io.fluxcapacitor.javaclient.publishing.CommandGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.fluxplaygroundspringboot.util.TestObjectCreator.createGameCommand;

@SpringBootTest
@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "integrationTest")
class FluxplaygroundSpringbootApplicationTests {

    @Autowired
    CommandGateway commandGateway;

    @Test
    void contextLoads() {
        commandGateway.sendAndWait(createGameCommand());
    }

}
