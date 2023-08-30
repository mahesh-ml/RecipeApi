package org.abn.recipe.recipemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.abn.recipe.recipemanagement.entity.Recipe;
import org.abn.recipe.recipemanagement.exception.ResourceNotFoundException;
import org.abn.recipe.recipemanagement.mapper.RecipeMapper;
import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.abn.recipe.recipemanagement.repository.RecipeRepository;
import org.abn.recipe.recipemanagement.service.IRecipeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements IRecipeService {

    private final RecipeMapper recipeMapper;
    private final RecipeRepository recipeRepository;

    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(recipeMapper::recipeToRecipeDto)
                .collect(Collectors.toList());
    }

    public Optional<RecipeDto> getRecipeById(Long id) {
       return recipeRepository.findById(id).map(recipeMapper::recipeToRecipeDto);
    }

    public RecipeDto createRecipe(RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.recipeToRecipeDto(savedRecipe);
    }

    public RecipeDto updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe" , "recipeId" , id));
        recipe.setName(recipeDto.getName());
        recipe.setVegetarian(recipeDto.isVegetarian());
        recipe.setServings(recipeDto.getServings());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setInstructions(recipeDto.getInstructions());
        Recipe updatedRecipe = recipeRepository.save(recipe);
        return recipeMapper.recipeToRecipeDto(updatedRecipe);
    }

    public String deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe" , "recipeId" , id));
        recipeRepository.delete(recipe);
        return "Recipe with Id " + id + " deleted";
    }




}
