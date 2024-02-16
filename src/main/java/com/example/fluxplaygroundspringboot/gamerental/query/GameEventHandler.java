package com.example.fluxplaygroundspringboot.gamerental.query;

import com.example.fluxplaygroundspringboot.gamerental.api.GameCreatedEvent;
import com.example.fluxplaygroundspringboot.gamerental.api.GameRentedEvent;
import com.example.fluxplaygroundspringboot.gamerental.api.GameReturnedEvent;
import io.fluxcapacitor.javaclient.tracking.handling.HandleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameEventHandler {
    @HandleEvent
    void handle(GameCreatedEvent gameCreatedEvent) {
        log.info("Handling the gameCreatedEvent");
    }

    @HandleEvent
    void handle(GameRentedEvent gameRentedEvent) {
        log.info("Handling the gameRentedEvent");
    }

    @HandleEvent
    void handle(GameReturnedEvent gameReturnedEvent) {
        log.info("Handling the gameReturnedEvent");
    }
}
