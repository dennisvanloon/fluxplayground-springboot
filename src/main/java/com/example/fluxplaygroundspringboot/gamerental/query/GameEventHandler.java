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

    private final GamesRepository gamesRepository;

    public GameEventHandler(GamesRepository gamesRepository) {
        this.gamesRepository = gamesRepository;
    }

    @HandleEvent
    void handle(GameCreatedEvent gameCreatedEvent) {
        log.info("Handling the gameCreatedEvent");
        gamesRepository.save(Game.builder().gameId(gameCreatedEvent.getGameId()).build());
        gamesRepository.count();
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
