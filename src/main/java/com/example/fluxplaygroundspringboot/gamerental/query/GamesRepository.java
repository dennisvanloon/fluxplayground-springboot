package com.example.fluxplaygroundspringboot.gamerental.query;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GamesRepository extends ElasticsearchRepository<Game, String> {
}
