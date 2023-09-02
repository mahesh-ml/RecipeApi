package org.abn.recipe.recipemanagement.repository;

import org.abn.recipe.recipemanagement.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long>, JpaSpecificationExecutor<Recipe> {
    List<Recipe> findByInstructionsContaining(String keyword);
}
