package org.abn.recipe.recipemanagement.service;

import org.abn.recipe.recipemanagement.payload.RecipeDto;

import java.util.List;
import java.util.Optional;

public interface IRecipeService {
    List<RecipeDto> getAllRecipes();
    Optional<RecipeDto> getRecipeById(Long id);
    RecipeDto createRecipe(RecipeDto recipeDto);
    RecipeDto updateRecipe(Long id, RecipeDto recipeDto);
    String deleteRecipe(Long id);

}
