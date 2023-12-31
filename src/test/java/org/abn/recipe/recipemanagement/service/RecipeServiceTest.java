package org.abn.recipe.recipemanagement.service;


import org.abn.recipe.recipemanagement.entity.Recipe;
import org.abn.recipe.recipemanagement.exception.InvalidPayloadException;
import org.abn.recipe.recipemanagement.exception.ResourceNotFoundException;
import org.abn.recipe.recipemanagement.mapper.RecipeMapper;
import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.abn.recipe.recipemanagement.repository.RecipeRepository;
import org.abn.recipe.recipemanagement.search.SearchCriteria;
import org.abn.recipe.recipemanagement.search.SearchOperation;
import org.abn.recipe.recipemanagement.service.impl.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;
    @InjectMocks
    private RecipeServiceImpl classUnderTest;
    private RecipeDto recipeDto1;
    private RecipeDto recipeDto2;
    private Recipe recipe;
    private Recipe recipeWithId;
    Long recipeId;
    @Mock
    private RecipeMapper recipeMapper;

    @BeforeEach
    public void init() {
        recipeId = 11L;
        List<String> ingredients1 = List.of("ingredient1", "ingredient2");
        List<String> ingredients2 = List.of("ingredient3", "ingredient4");

        recipeDto1 = RecipeDto.builder()
                .name("Recipe 1")
                .vegetarian(true)
                .servings(4)
                .ingredients(ingredients1)
                .instructions("Instruction1")
                .build();

        recipeDto2 = RecipeDto.builder()
                .name("Recipe 2")
                .vegetarian(false)
                .servings(2)
                .ingredients(ingredients2)
                .instructions("Instruction2")
                .build();

        recipe = new Recipe("Test Recipe", true, 4, List.of("ingredient1", "ingredient2"),
                "Instructions1");

        recipeWithId = new Recipe(1L, "Test Recipe", true, 4, List.of("ingredient1", "ingredient2"),
                "Instructions1");

    }

    @Test
    @DisplayName("Service Class Test -> create recipe")
    void givenRecipe_whenSaveRecipe_thenRecipeSaved() {

        when(recipeMapper.recipeDtoToRecipe(recipeDto1)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenReturn(recipe);
        when(recipeMapper.recipeToRecipeDto(recipe)).thenReturn(recipeDto1);

        RecipeDto createdDto = classUnderTest.createRecipe(recipeDto1);

        assertThat(createdDto).isEqualTo(recipeDto1);

    }


    @Test
    @DisplayName("Service Class Test -> Find All recipes")
    void givenRecipe_whenFindAllRecipe_thenListAllRecipes() {
        List<Recipe> recipes = Arrays.asList(
                recipe,
                new Recipe("Test Recipe1", false, 2, List.of("ingredient3", "ingredient4"),
                        "Instructions2")
        );
        List<RecipeDto> expectedDtos = List.of(recipeDto1, recipeDto2);

        when(recipeRepository.findAll()).thenReturn(recipes);
        when(recipeMapper.recipeToRecipeDto(any(Recipe.class))).thenAnswer(invocation -> {
            Recipe recipe = invocation.getArgument(0);
            return RecipeDto.builder()
                    .name(recipe.getName()).vegetarian(recipe.isVegetarian())
                    .servings(recipe.getServings()).ingredients(recipe.getIngredients()).instructions(recipe.getInstructions()).build();
        });

        List<RecipeDto> actualRecipeDtos = classUnderTest.getAllRecipes();

        assertThat(actualRecipeDtos.size()).isEqualTo(expectedDtos.size());
    }

    @Test
    @DisplayName("Service Class Test -> Recipe Save Error ")
    void givenInvalidRecipe_whenSave_InvalidRecipeNotSaved_Error() {

        when(recipeMapper.recipeDtoToRecipe(recipeDto1)).thenReturn(recipe);
        when(recipeRepository.save(recipe)).thenThrow(new RuntimeException("Failed to save recipe"));

        assertThrows(RuntimeException.class, () -> classUnderTest.createRecipe(recipeDto1));
    }

    @Test
    @DisplayName("Service Class Test -> Recipe Update Successful ")
    void givenRecipeIdAndRecipe_whenUpdateRecipe_thenRecipeUpdated() {

        RecipeDto expectedRecipeDto = recipeDto1;

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeWithId));
        when(recipeRepository.save(recipeWithId)).thenReturn(recipeWithId);
        when(recipeMapper.recipeToRecipeDto(recipeWithId)).thenReturn(expectedRecipeDto);

        RecipeDto updatedRecipeDto = classUnderTest.updateRecipe(recipeId, recipeDto1);

        assertThat(updatedRecipeDto).isNotNull();
        assertThat(updatedRecipeDto).isEqualTo(expectedRecipeDto);
    }

    @Test
    @DisplayName("Service Class Test -> Recipe Update Recipe Not Found ")
    void givenInvalidRecipeIdAndRecipe_whenUpdateRecipe_thenThrow404() {

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> classUnderTest.updateRecipe(recipeId, recipeDto2));
    }

    @Test
    @DisplayName("Service Class Test -> Recipe Find By Id Success ")
    void givenRecipeId_whenFindById_thenReturnRecipe() {


        RecipeDto expectedDto = recipeDto1;

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipeWithId));
        when(recipeMapper.recipeToRecipeDto(recipeWithId)).thenReturn(expectedDto);

        Optional<RecipeDto> foundDto = classUnderTest.getRecipeById(recipeId);

        assertThat(foundDto).isPresent().contains(expectedDto);
    }

    @Test
    @DisplayName("Service Class Test -> Recipe Find By Id Fail ")
    void givenInvalidRecipeId_whenFindById_thenThrowNotFoundError() {


        when(recipeRepository.findById(recipeId)).thenReturn(Optional.empty());

        Optional<RecipeDto> foundDto = classUnderTest.getRecipeById(recipeId);

        assertThat(foundDto).isEmpty();
    }

    @Test
    @DisplayName("Service Class Test -> Delete By Id Success ")
    void givenRecipeId_whenDeleteById_thenRecipeDeleted() {

        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);

        when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        String result = classUnderTest.deleteRecipe(recipeId);

        verify(recipeRepository, times(1)).deleteById(recipeId);
        assertEquals("Recipe with Id " + recipeId + " Deleted", result);
    }

    @Test
    @DisplayName("Service Class Test -> Search By Single Criteria")
    void givenSingleCriteria_whenSearch_ThenReturnSearchResult() {

        List<SearchCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(new SearchCriteria("name", SearchOperation.EQUAL, "Recipe 1"));

        List<RecipeDto> expected = List.of(recipeDto1);
        when(recipeRepository.findAll(isA(Specification.class))).thenReturn(List.of(recipe));
        when(recipeMapper.recipeToRecipeDto(recipe)).thenReturn(recipeDto1);

        List<RecipeDto> result = classUnderTest.search(criteriaList);

        assertThat(expected).isEqualTo(result);
    }

    @Test
    @DisplayName("Service Class Test -> Search By Empty Criteria")
    void givenEmptyCriteria_whenSearch_ThenReturnEmpty() {
        assertThrows(InvalidPayloadException.class, () -> classUnderTest.search(new ArrayList<>()));
    }

    @Test
    @DisplayName("Service Class Test -> Search By Multi Criteria")
    void givenMultipleCriteria_whenSearch_ThenReturnResult() {
        List<SearchCriteria> criteriaList = List.of(
                new SearchCriteria("name", SearchOperation.EQUAL, "Recipe 1"),
                new SearchCriteria("servings", SearchOperation.EQUAL, 4)
        );
        when(recipeRepository.findAll(any(Specification.class))).thenReturn(List.of(recipe));
        when(recipeMapper.recipeToRecipeDto(recipe)).thenReturn(recipeDto1);


        List<RecipeDto> result = classUnderTest.search(criteriaList);
        assertEquals(1, result.size());
        assertThat(result.get(0)).isEqualTo(recipeDto1);

    }

    @Test
    @DisplayName("Service Class Test -> Search within Instructions")
    void givenSearchParam_whenSearchWIthinInstructions_ThenReturnResult() {

        String searchQuery = "boil";

        List<Recipe> recipes = List.of(new Recipe(), new Recipe());
        List<RecipeDto> expected = List.of(new RecipeDto(), new RecipeDto());

        when(recipeRepository.findByInstructionsContaining(searchQuery)).thenReturn(recipes);
        when(recipeMapper.recipeToRecipeDto(any(Recipe.class))).thenReturn(new RecipeDto());


        List<RecipeDto> actual = classUnderTest.searchWithInInstructions(searchQuery);


        assertEquals(expected.size(), actual.size());
        assertEquals(actual, expected);
    }


}
