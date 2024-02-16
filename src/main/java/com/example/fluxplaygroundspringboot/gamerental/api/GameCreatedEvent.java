package com.example.fluxplaygroundspringboot.gamerental.api;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.Set;

@Getter
@Builder
public class GameCreatedEvent {

    private String gameId;
    private String title;
    private int stock;
    private Instant releaseDate;

}
