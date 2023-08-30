package org.abn.recipe.recipemanagement.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Recipe {

    @Id
    private Long recipeId;
    private String name;
    private boolean vegetarian;
    private int servings;
    @ElementCollection
    private List<String> ingredients;
    private String instructions;
}
