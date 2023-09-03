package org.abn.recipe.recipemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.abn.recipe.recipemanagement.entity.Recipe;
import org.abn.recipe.recipemanagement.payload.RecipeDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeMapper {

    private final ModelMapper modelMapper;

    public RecipeDto recipeToRecipeDto(Recipe recipe) {
        return modelMapper.map(recipe, RecipeDto.class);
    }

    public Recipe recipeDtoToRecipe(RecipeDto recipeDto) {
        return modelMapper.map(recipeDto, Recipe.class);

    }

}
