package com.example.fluxplaygroundspringboot.util;

import com.example.fluxplaygroundspringboot.gamerental.api.CreateGameCommand;
import com.example.fluxplaygroundspringboot.gamerental.api.RentGameCommand;
import com.example.fluxplaygroundspringboot.gamerental.api.ReturnGameCommand;

import java.time.Instant;

public class TestObjectCreator {

    public final static String GAME_ID = "1245-99876";
    public final static int STOCK = 3;
    public final static String GAME_TITLE = "my perfect game";
    public final static String RENTER = "john the gamer";
    public final static Instant RELEASE_DATE = Instant.now();

    public static CreateGameCommand createGameCommand() {
        return CreateGameCommand.builder()
                .gameId(GAME_ID)
                .stock(STOCK)
                .title(GAME_TITLE)
                .releaseDate(RELEASE_DATE)
                .build();
    }

    public static CreateGameCommand createGameCommandWithoutStock() {
        return CreateGameCommand.builder()
                .gameId(GAME_ID)
                .stock(0)
                .title(GAME_TITLE)
                .releaseDate(RELEASE_DATE)
                .build();
    }

    public static RentGameCommand rentGameCommand() {
        return RentGameCommand.builder()
                .gameId(GAME_ID)
                .renter(RENTER)
                .build();
    }

    public static ReturnGameCommand returnGameCommand() {
        return ReturnGameCommand.builder()
                .gameId(GAME_ID)
                .renter(RENTER)
                .build();
    }

}
