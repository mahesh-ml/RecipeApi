package org.abn.recipe.recipemanagement.controller;


import lombok.RequiredArgsConstructor;
import org.abn.recipe.recipemanagement.exception.ResourceNotFoundException;
import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.abn.recipe.recipemanagement.service.IRecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final IRecipeService recipeService;

    @GetMapping
    public List<RecipeDto> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDto createRecipe(@RequestBody RecipeDto recipeDto) {
        return recipeService.createRecipe(recipeDto);
    }

    @GetMapping("/{recipeId}")
    public RecipeDto getRecipeById(@PathVariable("recipeId") Long recipeId) {
        return recipeService.getRecipeById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe" , "recipeId" , recipeId));
    }

    @PutMapping("/{recipeId}")
    public RecipeDto updateRecipe(@PathVariable("recipeId") Long recipeId, @RequestBody RecipeDto recipeDto) {
        return recipeService.updateRecipe(recipeId, recipeDto);
    }

    @DeleteMapping("/{recipeId}")
    public String deleteRecipe(@PathVariable("recipeId") Long recipeId) {
       return recipeService.deleteRecipe(recipeId);
    }

}
