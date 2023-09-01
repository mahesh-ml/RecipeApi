package org.abn.recipe.recipemanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Recipe Management Api")
                        .version("1.0.0")
                        .description("Recipe Management Api"));
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }


}
