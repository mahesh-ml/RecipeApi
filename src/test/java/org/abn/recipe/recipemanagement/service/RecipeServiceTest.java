package org.abn.recipe.recipemanagement.service;


import org.abn.recipe.recipemanagement.entity.Recipe;
import org.abn.recipe.recipemanagement.mapper.RecipeMapper;
import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.abn.recipe.recipemanagement.repository.RecipeRepository;
import org.abn.recipe.recipemanagement.service.impl.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    private RecipeMapper recipeMapper;
    @InjectMocks
    RecipeServiceImpl classUnderTest;

    RecipeDto recipeDto1;
    RecipeDto recipeDto2;


    @BeforeEach
    public void init() {
        List<String> ingredients1 = List.of("ingredient1", "ingredient2");
        List<String> ingredients2 = List.of("ingredient3", "ingredient4");
        recipeDto1 = RecipeDto.builder()
                .name("Recipe 1").vegetarian(true)
                .servings(4).ingredients(ingredients1).instructions("Instruction1").build();

        recipeDto2 = RecipeDto.builder()
                .name("Recipe 2").vegetarian(false)
                .servings(2).ingredients(ingredients2).instructions("Instruction2").build();

    }

    @Test
    @DisplayName("Service Class Test -> create recipe")
    void givenRecipe_whenSaveRecipe_thenRecipeSaved() {

        Recipe recipe = new Recipe(null, "Test Recipe", true,4, List.of("ingredient1", "ingredient2"),
                "Instructions1");

        when(recipeMapper.recipeDtoToRecipe(recipeDto1)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(recipeMapper.recipeToRecipeDto(recipe)).thenReturn(recipeDto1);

        RecipeDto createdDto = classUnderTest.createRecipe(recipeDto1);

        assertThat(createdDto).isEqualTo(recipeDto1);

    }


    @Test
    @DisplayName("Service Class Test -> Find All recipes")
    void givenStocks_whenFindAllRecipe_thenListAllRecipes() {
        List<Recipe> recipes = Arrays.asList(
                new Recipe(null, "Test Recipe", true,4, List.of("ingredient1", "ingredient2"),
                        "Instructions1"),
                new Recipe(null, "Test Recipe1", false,2, List.of("ingredient3", "ingredient4"),
                        "Instructions2")
        );
        List<RecipeDto> expectedDtos = List.of(recipeDto1,recipeDto2);

        when(recipeRepository.findAll()).thenReturn(recipes);
        when(recipeMapper.recipeToRecipeDto(any(Recipe.class))).thenAnswer(invocation -> {
            Recipe recipe = invocation.getArgument(0);
            return  RecipeDto.builder()
                    .name(recipe.getName()).vegetarian(recipe.isVegetarian())
                    .servings(recipe.getServings()).ingredients(recipe.getIngredients()).instructions(recipe.getInstructions()).build();
        });

        List<RecipeDto> actualStockDtos = classUnderTest.getAllRecipes();

        assertThat(actualStockDtos.size()).isEqualTo(expectedDtos.size());
    }

    @Test
    @DisplayName("Service Class Test -> Recipe Save Error ")
    void givenInvalidStock_whenSave_InvalidStockNotSaved_Error() {
        Recipe recipe =    new Recipe(null, "Test Recipe", true,4, List.of("ingredient1", "ingredient2"),
                "Instructions1");

        when(recipeMapper.recipeDtoToRecipe(recipeDto1)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenThrow(new RuntimeException("Failed to save stock"));

        assertThrows(RuntimeException.class, () -> classUnderTest.createRecipe(recipeDto1));
    }

}
