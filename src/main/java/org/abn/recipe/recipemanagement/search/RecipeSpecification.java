package org.abn.recipe.recipemanagement.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.abn.recipe.recipemanagement.entity.Recipe;
import org.abn.recipe.recipemanagement.exception.InvalidPayloadException;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class RecipeSpecification implements Specification<Recipe> {

    private final SearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        switch (criteria.getOperation()) {
            case EQUAL:
                return equalPredicate(root, builder);
            case NOT_EQUAL:
                return notEqualPredicate(root, builder);
            case GREATER_THAN:
                return greaterThanPredicate(root, builder);
            case LESS_THAN:
                return lessThanPredicate(root, builder);
            case GREATER_THAN_OR_EQUAL:
                return greaterThanOrEqualPredicate(root, builder);
            case LESS_THAN_OR_EQUAL:
                return lessThanOrEqualPredicate(root, builder);
            case LIKE:
                return likePredicate(root, builder);
            case NOT_LIKE:
                return notLikePredicate(root, builder);
            case IN:
                return inPredicate(root, builder);
            case NOT_IN:
                return notInPredicate(root, builder);
            default:
                throw new InvalidPayloadException("Invalid operation: " + criteria.getOperation());
        }


    }

    private Predicate equalPredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return builder.equal(root.get(criteria.getSearchKey()), criteria.getValue());
    }

    private Predicate notEqualPredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return builder.notEqual(root.get(criteria.getSearchKey()), criteria.getValue());
    }

    private Predicate greaterThanPredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return builder.greaterThan(root.get(criteria.getSearchKey()), (Comparable) criteria.getValue());
    }

    private Predicate lessThanPredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return builder.lessThan(root.get(criteria.getSearchKey()), (Comparable) criteria.getValue());
    }

    private Predicate greaterThanOrEqualPredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return builder.greaterThanOrEqualTo(root.get(criteria.getSearchKey()), (Comparable) criteria.getValue());
    }

    private Predicate lessThanOrEqualPredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return builder.lessThanOrEqualTo(root.get(criteria.getSearchKey()), (Comparable) criteria.getValue());
    }

    private Predicate likePredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return builder.like(root.get(criteria.getSearchKey()), "%" + criteria.getValue() + "%");
    }

    private Predicate notLikePredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return builder.notLike(root.get(criteria.getSearchKey()), "%" + criteria.getValue() + "%");
    }

    private Predicate inPredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return root.get(criteria.getSearchKey()).in(criteria.getValue());
    }

    private Predicate notInPredicate(Root<Recipe> root, CriteriaBuilder builder) {
        return builder.not(root.get(criteria.getSearchKey()).in(criteria.getValue()));
    }


}
