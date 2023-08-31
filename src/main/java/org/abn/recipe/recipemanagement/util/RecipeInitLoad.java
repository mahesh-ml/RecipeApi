package org.abn.recipe.recipemanagement.util;

import lombok.RequiredArgsConstructor;
import org.abn.recipe.recipemanagement.entity.Recipe;
import org.abn.recipe.recipemanagement.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeInitLoad implements CommandLineRunner {
    private final RecipeRepository recipeRepository;

    List<Recipe> buildFewRecipes() {
        var recipe1 = new Recipe( "Biryani", false,4, List.of("chicken", "rice"),
                "Cook in bolied water and serve");
        var recipe2 = new Recipe( "Potato Wedges", true,1, List.of("Potato", "bread"),
                "Mash potatoes and toast the bread and fill");

        return List.of(recipe1,recipe2);
    }

    @Override
    public void run(String... args) {
        recipeRepository.saveAll(buildFewRecipes());
    }
}
