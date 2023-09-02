package org.abn.recipe.recipemanagement.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.abn.recipe.recipemanagement.exception.ResourceNotFoundException;
import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.abn.recipe.recipemanagement.search.SearchCriteria;
import org.abn.recipe.recipemanagement.service.IRecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final IRecipeService recipeService;


    @Operation(summary = "Get all recipes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all recipes", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RecipeDto.class)))
            })
    })
    @GetMapping
    public List<RecipeDto> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @Operation(summary = "Create a new recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe created successfully")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDto createRecipe(@RequestBody RecipeDto recipeDto) {
        return recipeService.createRecipe(recipeDto);
    }


    @Operation(summary = "Get a recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @GetMapping("/{recipeId}")
    public RecipeDto getRecipeById(@PathVariable Long recipeId) {
        return recipeService.getRecipeById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe" , "recipeId" , recipeId));
    }

    @Operation(summary = "Update a recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @PutMapping("/{recipeId}")
    public RecipeDto updateRecipe(@PathVariable Long recipeId, @RequestBody RecipeDto recipeDto) {
        return recipeService.updateRecipe(recipeId, recipeDto);
    }
    @Operation(summary = "Delete a recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @DeleteMapping("/{recipeId}")
    public String deleteRecipe(@PathVariable Long recipeId) {
       return recipeService.deleteRecipe(recipeId);
    }


    @Operation(summary = "Search recipes by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of recipes matching the criteria", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RecipeDto.class)))
            })
    })
    @PostMapping("/search")
    public List<RecipeDto> searchRecipes(@RequestBody List<SearchCriteria> criteriaList) {
        return recipeService.search(criteriaList);
    }


    @Operation(summary = "Search recipes with in instructions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of recipes matching the criteria", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RecipeDto.class)))
            })
    })
    @GetMapping ("/search/instructions")
    public List<RecipeDto> searchRecipeByInstructions(@RequestParam("q") String searchQuery){
        return recipeService.searchWithInInstructions(searchQuery);
    }
}
