package com.example.fluxplaygroundspringboot;

import com.example.fluxplaygroundspringboot.gamerental.query.GamesRepository;
import io.fluxcapacitor.javaclient.publishing.CommandGateway;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.util.Assert;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static com.example.fluxplaygroundspringboot.util.TestObjectCreator.createGameCommand;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(classes = { FluxplaygroundSpringbootApplication.class, FluxplaygroundSpringbootApplicationTests.TestConfiguration.class })
@EnabledIfSystemProperty(named = "spring.profiles.active", matches = "integrationTest")
@Testcontainers
class FluxplaygroundSpringbootApplicationTests {

    @Container
    private static final ElasticsearchContainer container = new ElasticsearchContainer(
            DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:8.7.0"))
            .withPassword("foobar")
            .withReuse(true);

    @Configuration
    static class TestConfiguration extends ElasticsearchConfiguration {
        @NotNull
        @Override
        public ClientConfiguration clientConfiguration() {

            Assert.notNull(container, "TestContainer is not initialized!");

            return ClientConfiguration.builder()
                    .connectedTo(container.getHttpHostAddress())
                    .usingSsl(container.createSslContextFromCa())
                    .withBasicAuth("elastic", "foobar")
                    .build();
        }
    }

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    GamesRepository repository;

    @Test
    void contextLoads() {
        commandGateway.sendAndWait(createGameCommand());
        await().atMost(5, SECONDS).until(() -> repository.count() == 1);
    }

}
