package org.abn.recipe.recipemanagement.service;

import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.abn.recipe.recipemanagement.search.SearchCriteria;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface IRecipeService {
    List<String> SEARCHABLE_FIELDS = Arrays.asList("instructions");
    List<RecipeDto> getAllRecipes();
    Optional<RecipeDto> getRecipeById(Long id);
    RecipeDto createRecipe(RecipeDto recipeDto);
    RecipeDto updateRecipe(Long id, RecipeDto recipeDto);
    String deleteRecipe(Long id);

    List<RecipeDto> search(List<SearchCriteria> criteriaList);

    List<RecipeDto> searchWithInInstructions(String searchQuery);


}
