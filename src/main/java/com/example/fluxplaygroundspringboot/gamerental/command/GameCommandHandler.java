package com.example.fluxplaygroundspringboot.gamerental.command;

import com.example.fluxplaygroundspringboot.gamerental.Game;
import com.example.fluxplaygroundspringboot.gamerental.api.*;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.modeling.Entity;
import io.fluxcapacitor.javaclient.tracking.handling.HandleCommand;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class GameCommandHandler {

    @HandleCommand
    void handle(CreateGameCommand createGameCommand) {
        Entity<Game> aggregate = FluxCapacitor.loadAggregate(createGameCommand.getGameId(), Game.class);

        if (aggregate.isPresent()) {
            throw new IllegalStateException("Game already exists");
        }

        aggregate.apply(GameCreatedEvent.builder()
                .gameId(createGameCommand.getGameId())
                .title(createGameCommand.getTitle())
                .stock(createGameCommand.getStock())
                .releaseDate(createGameCommand.getReleaseDate())
                .build());
    }

    @HandleCommand
    void handle(RentGameCommand rentGameCommand) {
        Entity<Game> aggregate = FluxCapacitor.loadAggregate(rentGameCommand.getGameId(), Game.class);

        if (!aggregate.isPresent()) {
            throw new IllegalStateException("Game does not exists");
        }

        if (aggregate.get().getStock() < 1) {
            throw new IllegalStateException("Game not available for rent");
        }

        aggregate.apply(GameRentedEvent.builder()
                .gameId(rentGameCommand.getGameId())
                .renter(rentGameCommand.getRenter())
                .build());
    }

    @HandleCommand
    void handle(ReturnGameCommand returnGameCommand) {
        Entity<Game> aggregate = FluxCapacitor.loadAggregate(returnGameCommand.getGameId(), Game.class);

        if (!aggregate.isPresent()) {
            throw new IllegalStateException("Game does not exists");
        }

        if (!aggregate.get().getRenters().contains(returnGameCommand.getRenter())) {
            throw new IllegalStateException("Game was not rented by renter");
        }

        aggregate.apply(GameReturnedEvent.builder()
                .gameId(returnGameCommand.getGameId())
                .renter(returnGameCommand.getRenter())
                .build());
    }
}
