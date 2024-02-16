package com.example.fluxplaygroundspringboot.gamerental;

import com.example.fluxplaygroundspringboot.gamerental.api.GameCreatedEvent;
import com.example.fluxplaygroundspringboot.gamerental.api.GameRentedEvent;
import com.example.fluxplaygroundspringboot.gamerental.api.GameReturnedEvent;
import io.fluxcapacitor.javaclient.FluxCapacitor;
import io.fluxcapacitor.javaclient.modeling.Aggregate;
import io.fluxcapacitor.javaclient.persisting.eventsourcing.Apply;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Aggregate
@Builder(toBuilder = true)
@Getter
public class Game {

    private String gameId;
    private String title;
    private int stock;
    private Instant releaseDate;
    private Set<String> renters;

    @Apply
    public static Game handle(GameCreatedEvent gameCreatedEvent) {
        return Game.builder()
                .gameId(gameCreatedEvent.getGameId())
                .title(gameCreatedEvent.getTitle())
                .stock(gameCreatedEvent.getStock())
                .releaseDate(gameCreatedEvent.getReleaseDate())
                .stock(gameCreatedEvent.getStock())
                .renters(new HashSet<>())
                .build();
    }

    @Apply
    public Game handle(GameRentedEvent gameRentedEvent) {
        Game game = FluxCapacitor.loadAggregate(gameRentedEvent.getGameId(), Game.class).get();
        Set<String> renters = game.getRenters();
        renters.add(gameRentedEvent.getRenter());

        return game.toBuilder()
                .stock(game.getStock() -1)
                .renters(renters)
                .build();
    }

    @Apply
    public Game handle(GameReturnedEvent gameReturnedEvent) {
        Game game = FluxCapacitor.loadAggregate(gameReturnedEvent.getGameId(), Game.class).get();
        Set<String> renters = game.getRenters();
        renters.remove(gameReturnedEvent.getRenter());

        return game.toBuilder()
                .stock(game.getStock() + 1)
                .renters(renters)
                .build();
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId='" + gameId + '\'' +
                ", stock=" + stock +
                ", title=" + title +
                ", releaseDate=" + releaseDate +
                ", renters=" + renters +
                '}';
    }
}
