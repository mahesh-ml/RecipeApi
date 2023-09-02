package org.abn.recipe.recipemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.abn.recipe.recipemanagement.entity.Recipe;
import org.abn.recipe.recipemanagement.exception.InvalidPayloadException;
import org.abn.recipe.recipemanagement.exception.ResourceNotFoundException;
import org.abn.recipe.recipemanagement.mapper.RecipeMapper;
import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.abn.recipe.recipemanagement.repository.RecipeRepository;
import org.abn.recipe.recipemanagement.search.RecipeSpecification;
import org.abn.recipe.recipemanagement.search.SearchCriteria;
import org.abn.recipe.recipemanagement.service.IRecipeService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements IRecipeService {

    private final RecipeMapper recipeMapper;
    private final RecipeRepository recipeRepository;



    @Override
    public List<RecipeDto> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(recipeMapper::recipeToRecipeDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RecipeDto> getRecipeById(Long id) {
       return recipeRepository.findById(id).map(recipeMapper::recipeToRecipeDto);
    }

    @Override
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.recipeDtoToRecipe(recipeDto);
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeMapper.recipeToRecipeDto(savedRecipe);
    }

    @Override
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

    @Override
    public String deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe" , "recipeId" , id));
        recipeRepository.deleteById(recipe.getRecipeId());
        return String.format("Recipe with Id %d Deleted",id);
    }

    @Override
    public List<RecipeDto> search(List<SearchCriteria> criteriaList) {
        List<Specification<Recipe>> specifications = criteriaList.stream()
                .map(RecipeSpecification::new)
                .collect(Collectors.toList());

        Specification<Recipe> finalSpecification = specifications.stream()
                .reduce(Specification::and)
                .orElseThrow(() -> new InvalidPayloadException("Invalid Specification For search "));

        return recipeRepository.findAll(finalSpecification).stream()
                .map(recipeMapper::recipeToRecipeDto)
                .collect(Collectors.toList());


    }

    @Override
    public List<RecipeDto> searchWithInInstructions(String searchQuery) {
       return recipeRepository.findByInstructionsContaining(searchQuery).stream()
               .map(recipeMapper::recipeToRecipeDto)
               .collect(Collectors.toList());
    }


}
