package org.abn.recipe.recipemanagement.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiConstant {

    INVALID_PAYLOAD_ERROR_MESSAGE("Error in Processing update Request Payload "),
    API_BASE_URL("/recipes"),
    API_BASE_URL_WITH_ID("/recipes/{recipeId}"),
    API_SEARCH_URL("/recipes/search"),
    API_SEARCH_WITHIN_INSTRUCTIONS("/recipes/search/instructions"),
    POSTGRESQL_TC_DOCKER_IMAGE("postgres:11.1"),
    POSTGRESQL_TC_DBNAME("test"),
    POSTGRESQL_TC_USERNAME("sa"),
    POSTGRESQL_TC_PWD("sa"),
    SPRING_DATASOURCE_TC_URL_PROP_KEY("spring.datasource.url"),
    SPRING_DATASOURCE_TC_USERNAME_PROP_KEY("spring.datasource.username"),
    SPRING_DATASOURCE_TC_PWD_PROP_KEY("spring.datasource.password");



    final String value;
}
