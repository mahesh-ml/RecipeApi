package org.abn.recipe.recipemanagement.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.abn.recipe.recipemanagement.entity.Recipe;
import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.abn.recipe.recipemanagement.repository.RecipeRepository;
import org.abn.recipe.recipemanagement.search.SearchCriteria;
import org.abn.recipe.recipemanagement.search.SearchOperation;
import org.abn.recipe.recipemanagement.util.ApiConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
public class ITRecipeApiTest extends BaseIntegrationTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private RecipeDto recipeDto;

    @Autowired
    private RecipeRepository recipeRepository;

    private Recipe recipe1;

    private Recipe recipe2;


    @BeforeEach
    public void setup() {

        recipeRepository.deleteAll();

        List<String> ingredients1 = List.of("ingredient1", "ingredient2");
        recipeDto = RecipeDto.builder()
                .name("Test Recipe").vegetarian(true)
                .servings(4).ingredients(ingredients1).instructions("Instructions1").build();

        recipe1 = new Recipe("Test Recipe", true, 4, List.of("ingredient1", "ingredient2"),
                "Instructions1");

        recipe2 = new Recipe("Test Recipe1", false, 4, List.of("ingredient3", "ingredient4"),
                "Instructions2");

    }

    @Test
    @DisplayName("Integration Test -> Test find all recipe")
    public void givenRecipeList_whenFindAll_thenReturnAllRecipes() throws Exception {

        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        ResultActions result = mockMvc.perform(get(ApiConstant.API_BASE_URL.getValue()));


        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));

    }

    @Test
    @DisplayName("Integration Test -> find recipe by Id")
    public void givenRecipeId_whenGetByRecipeId_thenReturnRecipe() throws Exception {

        recipeRepository.save(recipe1);

        ResultActions result = mockMvc
                .perform(get(ApiConstant.API_BASE_URL_WITH_ID.getValue(), recipe1.getRecipeId()));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipeDto.getName())))
                .andExpect(jsonPath("$.servings").value(recipeDto.getServings()))
                .andExpect(jsonPath("$.instructions").value(recipeDto.getInstructions()));

    }

    @Test
    @DisplayName("Integration Test -> find Recipe By Recipe Id - Not Found")
    public void givenInValidRecipeId_whenGetByRecipeId_thenReturnNotFound() throws Exception {
        Long recipeId = 111L;

        mockMvc.perform(get(ApiConstant.API_BASE_URL_WITH_ID.getValue(), recipeId))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Integration Test -> Delete recipe By Id ")
    public void givenRecipeId_whenDeleteRecipe_thenRecipeDeleted() throws Exception {
        recipeRepository.save(recipe1);

        String expectedResponse = "Recipe with Id " + recipe1.getRecipeId() + " Deleted";
        ResultActions result = mockMvc.perform(delete(ApiConstant.API_BASE_URL_WITH_ID.getValue(), recipe1.getRecipeId()));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(expectedResponse)));

    }

    @Test
    @DisplayName("Integration Test -> update Recipe")
    void givenRecipeAndId_whenUpdateRecipe_thenRecipeUpdated() throws Exception {
        recipe1.setName("xyz");
        recipeRepository.save(recipe1);

        mockMvc.perform(put(ApiConstant.API_BASE_URL_WITH_ID.getValue(), recipe1.getRecipeId())
                        .content(jsonString(recipeDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipeDto.getName())))
                .andExpect(jsonPath("$.servings").value(recipeDto.getServings()))
                .andExpect(jsonPath("$.instructions").value(recipeDto.getInstructions()));
    }

    @Test
    @DisplayName("Integration Test -> create recipe ")
    public void givenRecipe_whenCreateRecipe_thenReturnRecipeCreated() throws Exception {
        recipeRepository.save(recipe1);

        mockMvc.perform(post(ApiConstant.API_BASE_URL.getValue())
                        .content(jsonString(recipeDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(recipeDto.getName())))
                .andExpect(jsonPath("$.servings").value(recipeDto.getServings()))
                .andExpect(jsonPath("$.instructions").value(recipeDto.getInstructions()));
    }

    @Test
    @DisplayName("Integration Test -> Search By Multi Criteria")
    void givenCriteria_whenSearch_ThenReturnResult() throws Exception {
        List<SearchCriteria> criteriaList = List.of(
                new SearchCriteria("servings", SearchOperation.EQUAL, 4)
        );

        recipeRepository.save(recipe1);

        mockMvc.perform(post(ApiConstant.API_SEARCH_URL.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criteriaList)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name", is(recipeDto.getName())))
                .andExpect(jsonPath("$[0].servings").value(recipeDto.getServings()))
                .andExpect(jsonPath("$[0].instructions").value(recipeDto.getInstructions()));


    }

    @Test
    @DisplayName("Integration Test -> Search within Instructions")
    void givenSearchParam_whenSearchWIthinInstructions_ThenReturnResult() throws Exception {

        String searchQuery = "boil";
        Recipe recipeForSearch = new Recipe("Test Recipe1", true, 4,
                List.of("ingredient1", "ingredient2"),
                "boil water and place in oven");


        recipeRepository.save(recipeForSearch);


        RecipeDto recipeSearchDto = RecipeDto.builder()
                .name("Test Recipe1").vegetarian(true)
                .servings(4).ingredients(List.of("ingredient1", "ingredient2")).instructions("boil water and place in oven").build();

        mockMvc.perform(get(ApiConstant.API_SEARCH_WITHIN_INSTRUCTIONS.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("q", searchQuery))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].name", is(recipeSearchDto.getName())))
                .andExpect(jsonPath("$[0].servings").value(recipeSearchDto.getServings()))
                .andExpect(jsonPath("$[0].instructions").value(recipeSearchDto.getInstructions()));

    }

    @Test
    @DisplayName("Integration Test -> Search within Instructions Empty")
    void givenSearchParam_whenSearchWIthinInstructions_ThenReturnEmpty() throws Exception {

        String searchQuery = "xyz";
        Recipe recipeForSearch = new Recipe("Test Recipe1", true, 4,
                List.of("ingredient1", "ingredient2"),
                "boil water and place in oven");
        recipeRepository.save(recipeForSearch);

        mockMvc.perform(get(ApiConstant.API_SEARCH_WITHIN_INSTRUCTIONS.getValue())
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("q", searchQuery))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(0)));

    }

    String jsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);

    }

}
