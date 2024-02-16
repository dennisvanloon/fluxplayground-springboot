package com.example.fluxplaygroundspringboot.gamerental.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RentGameCommand {
    private String gameId;
    private String renter;
}
