package org.abn.recipe.recipemanagement.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDto {

    private String name;
    private boolean vegetarian;
    private int servings;
    private List<String> ingredients;
    private String instructions;
}
