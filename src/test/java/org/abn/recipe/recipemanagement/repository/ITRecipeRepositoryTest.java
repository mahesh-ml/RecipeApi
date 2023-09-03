package org.abn.recipe.recipemanagement.repository;

import org.abn.recipe.recipemanagement.entity.Recipe;
import org.abn.recipe.recipemanagement.integration.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ITRecipeRepositoryTest extends BaseIntegrationTest {

    @Autowired
    RecipeRepository recipeRepository;

    Recipe recipe;

    @BeforeEach
    public void init() {

        recipeRepository.deleteAll();
        recipe = new Recipe("Test Recipe", true, 4, List.of("ingredient1", "ingredient2"),
                "Instructions1");

    }


    @Test
    @DisplayName("Data JPA test -> save operation")
    public void givenRecipeObject_whenSave_thenReturnSavedRecipe() {

        //when - action or behaviour that we are testing
        Recipe savedRecipe = recipeRepository.save(recipe);

        //then -verify the output
        assertThat(savedRecipe).isNotNull();
        assertThat(savedRecipe.getRecipeId()).isEqualTo(recipe.getRecipeId());
        assertThat(savedRecipe.getName()).isEqualTo(recipe.getName());
        assertThat(savedRecipe.getServings()).isEqualTo(recipe.getServings());
        assertThat(savedRecipe.getInstructions()).isEqualTo(recipe.getInstructions());

    }

    @Test
    @DisplayName("Data JPA test -> find All operation")
    public void givenRecipeList_whenFindAll_thenReturnRecipeList() {
        //given precondition or setup
        recipeRepository.save(recipe);

        //when - action or behaviour that we are testing
        List<Recipe> recipes = recipeRepository.findAll();

        //then -verify the output
        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(1);

    }


    @Test
    @DisplayName("Data JPA test -> find By Id operation")
    public void givenRecipeId_whenFindById_thenReturnRecipe() {
        //given precondition or setup

        recipeRepository.save(recipe);
        //when - action or behaviour that we are testing
        Optional<Recipe> foundRecipe = recipeRepository.findById(recipe.getRecipeId());

        //then -verify the output
        assertThat(foundRecipe).isNotEmpty();

    }

}
