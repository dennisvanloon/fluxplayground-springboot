package com.example.fluxplaygroundspringboot.gamerental.query;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Builder
@Document(indexName = "games")
public class Game {
    private @Id String gameId;

}
