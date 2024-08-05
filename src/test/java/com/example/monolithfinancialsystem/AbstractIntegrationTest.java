package com.example.monolithfinancialsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.FieldDefinitionBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "PT240S")
public abstract class AbstractIntegrationTest {

    @Autowired private WebApplicationContext webApplicationContext;
    @Autowired protected EntityManagerFactory entityManagerFactory;
    @Autowired protected ObjectMapper objectMapper;

    public static JdbcDatabaseContainer<?> postgres;

    protected static EnhancedRandom random;
    protected MockMvc mvc;

    static {
        postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:14"))
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("postgres");
        postgres.start();
        String DATABASE_URL = String.format("jdbc:postgresql://%s:%d/%s",
                postgres.getHost(),
                postgres.getFirstMappedPort(),
                postgres.getDatabaseName()
        );
        System.setProperty("DATABASE_URL", DATABASE_URL);

        random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .exclude(FieldDefinitionBuilder.field().named("id").get())
                .dateRange(LocalDate.of(2022, 1, 1), LocalDate.of(2024, 12, 31))
                .stringLengthRange(8, 11)
                .collectionSizeRange(0,0)
                .randomize(Integer.class, (Randomizer<Integer>) () -> ThreadLocalRandom.current().nextInt(11, 11))
                .randomize(Long.class, (Randomizer<Long>) () -> ThreadLocalRandom.current().nextLong(10000000000L, 99999999999L))
                .randomize(Double.class, (Randomizer<Double>) () -> BigDecimal.valueOf(
                        ThreadLocalRandom.current().nextDouble(0, 100)).setScale(2, RoundingMode.HALF_UP).doubleValue()
                )
                .randomize(BigDecimal.class, (Randomizer<BigDecimal>) () -> BigDecimal.valueOf(
                        ThreadLocalRandom.current().nextDouble(0, 100)).setScale(2, RoundingMode.HALF_UP)
                )
                .build();
    }

    @PostConstruct
    public void initAbstractIntegrationTest() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(print())
                .build();
    }
}
