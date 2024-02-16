package com.example.fluxplaygroundspringboot.gamerental.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GameRentedEvent {
    private String gameId;
    private String renter;
}
