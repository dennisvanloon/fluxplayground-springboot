package com.example.fluxplaygroundspringboot.gamerental;

import com.example.fluxplaygroundspringboot.gamerental.api.*;
import com.example.fluxplaygroundspringboot.gamerental.command.GameCommandHandler;
import com.example.fluxplaygroundspringboot.gamerental.query.GameEventHandler;
import io.fluxcapacitor.javaclient.modeling.Entity;
import io.fluxcapacitor.javaclient.test.TestFixture;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.example.fluxplaygroundspringboot.util.TestObjectCreator.*;
import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

public class GameRentalTest {

    private final TestFixture testFixture = TestFixture.create(new GameCommandHandler(), new GameEventHandler());

    @Test
    void testCreateGame() {
        testFixture.whenCommand(createGameCommand())
                .expectEvent(satisfies(event -> {
                    assertInstanceOf(GameCreatedEvent.class, event);
                    GameCreatedEvent gameCreatedEvent = (GameCreatedEvent)event;
                    assertEquals(STOCK
                            , gameCreatedEvent.getStock());
                    assertEquals(GAME_TITLE, gameCreatedEvent.getTitle());
                    assertEquals(RELEASE_DATE, gameCreatedEvent.getReleaseDate());
                }));

        Entity<Game> aggregate = testFixture.getFluxCapacitor().aggregateRepository().loadFor(GAME_ID, Game.class);
        assertTrue(aggregate.isPresent());
        assertEquals(GAME_ID, aggregate.get().getGameId());
        assertEquals(STOCK, aggregate.get().getStock());
        assertEquals(GAME_TITLE, aggregate.get().getTitle());
        assertEquals(RELEASE_DATE, aggregate.get().getReleaseDate());
        assertEquals(emptySet(), aggregate.get().getRenters());
    }

    @Test
    void testGameCannotBeCreatedTwice() {
        CreateGameCommand createGameCommand = createGameCommand();
        testFixture.givenCommands(createGameCommand)
                .whenCommand(createGameCommand)
                .expectError(throwable ->
                        throwable.getClass().equals(IllegalStateException.class) && throwable.getMessage().equals("Game already exists"))
                .expectNoEvents();
    }

    @Test
    void testRentAGame() {
        testFixture.givenCommands(createGameCommand())
                .whenCommand(rentGameCommand())
                .expectEvent(event ->
                        event instanceof GameRentedEvent gameRentedEvent &&
                                gameRentedEvent.getGameId().equals(GAME_ID) &&
                                gameRentedEvent.getRenter().equals(RENTER)
                );

        Entity<Game> aggregate = testFixture.getFluxCapacitor().aggregateRepository().loadFor(GAME_ID, Game.class);
        assertTrue(aggregate.isPresent());
        assertEquals(GAME_ID, aggregate.get().getGameId());
        assertEquals(STOCK - 1, aggregate.get().getStock());
        assertEquals(GAME_TITLE, aggregate.get().getTitle());
        assertEquals(RELEASE_DATE, aggregate.get().getReleaseDate());
        assertEquals(Sets.set(RENTER), aggregate.get().getRenters());
    }

    @Test
    void testReturnAGame() {
        testFixture.givenCommands(createGameCommand(), rentGameCommand())
                .whenCommand(returnGameCommand())
                .expectEvent(event ->
                        event instanceof GameReturnedEvent gameReturnedEvent &&
                                gameReturnedEvent.getGameId().equals(GAME_ID) &&
                                gameReturnedEvent.getRenter().equals(RENTER)
                );

        Entity<Game> aggregate = testFixture.getFluxCapacitor().aggregateRepository().loadFor(GAME_ID, Game.class);
        assertTrue(aggregate.isPresent());
        assertEquals(GAME_ID, aggregate.get().getGameId());
        assertEquals(STOCK, aggregate.get().getStock());
        assertEquals(GAME_TITLE, aggregate.get().getTitle());
        assertEquals(RELEASE_DATE, aggregate.get().getReleaseDate());
        assertEquals(emptySet(), aggregate.get().getRenters());
    }

    @Test
    void testGameCannotBeRentedWhenNoStock() {
        testFixture.givenCommands(createGameCommandWithoutStock())
                .whenCommand(rentGameCommand())
                .expectError(throwable ->
                        throwable.getClass().equals(IllegalStateException.class) && throwable.getMessage().equals("Game not available for rent"))
                .expectNoEvents();
    }

    public static <T> Predicate<T> satisfies(Consumer<T> assertions) {
        return (t) -> {
            assertions.accept(t);
            return true;
        };
    }

}
