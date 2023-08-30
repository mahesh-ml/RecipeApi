package org.abn.recipe.recipemanagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.abn.recipe.recipemanagement.service.IRecipeService;
import org.abn.recipe.recipemanagement.util.ApiConstant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class RecipeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    IRecipeService recipeService;

    @Autowired
    ObjectMapper objectMapper;
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
    @DisplayName("Test find all recipe")
    public void givenRecipeList_whenFindAll_thenReturnAllRecipes() throws Exception {
        //given precondition or setup
        given(recipeService.getAllRecipes()).willReturn(List.of(recipeDto1,recipeDto2));

        //when - action or behaviour that we are testing
        ResultActions result = mockMvc.perform(get(ApiConstant.API_BASE_URL.getValue()));

        //then -verify the output
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)));

    }

    @Test
    @DisplayName("Test find recipe by Id")
    public void givenRecipeId_whenGetByRecipeId_thenReturnRecipe() throws Exception {
        //given precondition or setup
        Long id = 1L;
        given(recipeService.getRecipeById(id)).willReturn(Optional.of(recipeDto1));

        //when - action or behaviour that we are testing
        ResultActions result = mockMvc
                .perform(get(ApiConstant.API_BASE_URL_WITH_ID.getValue(), id));


        //then -verify the output
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipeDto1.getName())))
                .andExpect(jsonPath("$.servings").value(recipeDto1.getServings()))
                .andExpect(jsonPath("$.instructions").value(recipeDto1.getInstructions()));

    }

    @Test
    @DisplayName("Test find Recipe By Recipe Id - Not Found")
    public void givenInValidRecipeId_whenGetByRecipeId_thenReturnNotFound() throws Exception {
        Long recipeId = 111L;
        Mockito.when(recipeService.getRecipeById(recipeId)).thenReturn(Optional.empty());

        mockMvc.perform(get(ApiConstant.API_BASE_URL_WITH_ID.getValue(), recipeId))
                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.verify(recipeService, Mockito.times(1)).getRecipeById(recipeId);
    }

    @Test
    @DisplayName("Test Delete recipe By Id ")
    public void givenRecipeId_whenDeleteRecipe_thenRecipeDeleted() throws Exception {
        //given precondition or setup
        Long recipeId = 11L;
        String expectedResponse ="Deleted Recipe with id " + recipeId;

        given(recipeService.deleteRecipe(recipeId)).willReturn(expectedResponse);

        //when - action or behaviour that we are testing
        ResultActions result = mockMvc.perform(delete(ApiConstant.API_BASE_URL_WITH_ID.getValue(), recipeId));

        //then -verify the output
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(expectedResponse)));

        Mockito.verify(recipeService, Mockito.times(1)).deleteRecipe(recipeId);

    }

    @Test
    @DisplayName("Test update Recipe")
    void givenRecipeAndId_whenUpdateRecipe_thenRecipeUpdated() throws Exception {
        Long recipeId = 1L;


        given(recipeService.updateRecipe(anyLong(), any(RecipeDto.class))).willReturn(recipeDto1);

        mockMvc.perform(put(ApiConstant.API_BASE_URL_WITH_ID.getValue(), recipeId)
                        .content(jsonString(recipeDto1))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(recipeDto1.getName())))
                .andExpect(jsonPath("$.servings").value(recipeDto1.getServings()))
                .andExpect(jsonPath("$.instructions").value(recipeDto1.getInstructions()));
    }

    @Test
    @DisplayName("Test create recipe ")
    public void givenRecipe_whenCreateRecipe_thenReturnRecipeCreated() throws Exception {

        //given precondition or setup
        given(recipeService.createRecipe(any(RecipeDto.class))).willReturn(recipeDto2);

        //when - action or behaviour that we are testing
        mockMvc.perform(post(ApiConstant.API_BASE_URL.getValue())
                        .content(jsonString(recipeDto2))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(recipeDto2.getName())))
                .andExpect(jsonPath("$.servings").value(recipeDto2.getServings()))
                .andExpect(jsonPath("$.instructions").value(recipeDto2.getInstructions()));
    }

     String jsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);

    }

}
