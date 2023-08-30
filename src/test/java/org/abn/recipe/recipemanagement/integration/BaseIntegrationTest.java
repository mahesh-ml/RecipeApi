package org.abn.recipe.recipemanagement.integration;

import org.abn.recipe.recipemanagement.util.ApiConstant;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ActiveProfiles("integration")
public class BaseIntegrationTest {
    @Container
    final static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer(
            ApiConstant.POSTGRESQL_TC_DOCKER_IMAGE.getValue())
            .withDatabaseName(ApiConstant.POSTGRESQL_TC_DBNAME.getValue())
            .withUsername(ApiConstant.POSTGRESQL_TC_USERNAME.getValue())
            .withPassword(ApiConstant.POSTGRESQL_TC_PWD.getValue());


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add(ApiConstant.SPRING_DATASOURCE_TC_URL_PROP_KEY.getValue(),
                postgresqlContainer::getJdbcUrl);
        registry.add(ApiConstant.SPRING_DATASOURCE_TC_USERNAME_PROP_KEY.getValue(),
                postgresqlContainer::getUsername);
        registry.add(ApiConstant.SPRING_DATASOURCE_TC_PWD_PROP_KEY.getValue(),
                postgresqlContainer::getPassword);
    }
}
