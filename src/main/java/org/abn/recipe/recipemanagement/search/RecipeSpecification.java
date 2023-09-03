package org.abn.recipe.recipemanagement.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.abn.recipe.recipemanagement.entity.Recipe;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class RecipeSpecification implements Specification<Recipe> {
    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        return switch (criteria.getOperation()) {
            case EQUAL -> builder.equal(root.get(criteria.getSearchKey()), criteria.getValue());
            case NOT_EQUAL -> builder.notEqual(root.get(criteria.getSearchKey()), criteria.getValue());
            case GREATER_THAN ->
                    builder.greaterThan(root.get(criteria.getSearchKey()), (Comparable) criteria.getValue());
            case LESS_THAN -> builder.lessThan(root.get(criteria.getSearchKey()), (Comparable) criteria.getValue());
            case GREATER_THAN_OR_EQUAL ->
                    builder.greaterThanOrEqualTo(root.get(criteria.getSearchKey()), (Comparable) criteria.getValue());
            case LESS_THAN_OR_EQUAL ->
                    builder.lessThanOrEqualTo(root.get(criteria.getSearchKey()), (Comparable) criteria.getValue());
            case LIKE -> builder.like(root.get(criteria.getSearchKey()), "%" + criteria.getValue() + "%");
            case NOT_LIKE -> builder.notLike(root.get(criteria.getSearchKey()), "%" + criteria.getValue() + "%");
            case IN -> root.get(criteria.getSearchKey()).in(criteria.getValue());
            case NOT_IN -> builder.not(root.get(criteria.getSearchKey()).in(criteria.getValue()));
        };


    }
}
