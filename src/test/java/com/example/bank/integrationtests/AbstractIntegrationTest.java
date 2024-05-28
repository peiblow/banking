package com.example.bank.integrationtests;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        static PostgreSQLContainer<?> psql = new PostgreSQLContainer<>("postgres");

        private static void startContainers() {
            Startables.deepStart(Stream.of(psql)).join();
        }

        private static Map<String, String> createConnectionConfiguration() {
            return Map.of(
                    "spring.datasource.url", psql.getJdbcUrl(),
                    "spring.datasource.username", psql.getUsername(),
                    "spring.datasource.password", psql.getPassword()
            );
        }

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment env = applicationContext.getEnvironment();
            MapPropertySource testContainer = new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());

            env.getPropertySources().addFirst(testContainer);
        }
    }
}
