package org.abn.recipe.recipemanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.util.List;

@Indexed
@Entity
@Table(name = "recipe")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;
    private String name;
    private boolean vegetarian;
    private int servings;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> ingredients;

    @FullTextField
    private String instructions;

    public Recipe(String name, boolean vegetarian, int servings, List<String> ingredients, String instructions) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.servings = servings;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }


}
