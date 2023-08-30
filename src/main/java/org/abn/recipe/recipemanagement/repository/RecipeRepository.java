package org.abn.recipe.recipemanagement.repository;

import org.abn.recipe.recipemanagement.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
}
