package com.example.fluxplaygroundspringboot.gamerental.api;

import lombok.*;

import java.time.Instant;

@Getter
@Builder
public class CreateGameCommand {

    private String gameId;
    private String title;
    private int stock;
    private Instant releaseDate;

}
