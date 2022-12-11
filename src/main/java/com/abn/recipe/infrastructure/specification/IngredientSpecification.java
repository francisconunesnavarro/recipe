package com.abn.recipe.infrastructure.specification;

import java.util.ArrayList;
import java.util.UUID;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.abn.recipe.domain.model.IngredientQuery;
import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Ingredient_;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.infrastructure.entity.Recipe_;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 * Specification for Ingredient.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IngredientSpecification implements Specification<Ingredient> {
    
    IngredientQuery ingredientQuery;
    
    public IngredientSpecification(@NonNull final IngredientQuery ingredientQuery) {
        this.ingredientQuery = ingredientQuery;
    }
    
    @Override
    public Predicate toPredicate(
        @NonNull final Root<Ingredient> root,
        @NonNull final CriteriaQuery<?> query,
        @NonNull final CriteriaBuilder builder
    ) {
        addJoin(root, builder);
        return builder.and(new ArrayList<Predicate>().toArray(new Predicate[0]));
    }
    
    private void addJoin(
        @NonNull final Root<Ingredient> root,
        @NonNull final CriteriaBuilder builder
    ) {
        appendRecipeId(root, builder);
    }
    
    private void appendRecipeId(
        @NonNull final Root<Ingredient> root,
        @NonNull final CriteriaBuilder builder
    ) {
        ingredientQuery
            .recipeId()
            .ifPresent(recipeId -> appendJoinEqualRecipeId(root, builder, recipeId));
    }
    
    private void appendJoinEqualRecipeId(
        @NonNull final Root<Ingredient> root,
        @NonNull final CriteriaBuilder builder,
        @NonNull final UUID recipeId
    ) {
        final Join<Ingredient, Recipe> ingredientRecipeJoin = root.join(Ingredient_.recipe, JoinType.INNER);
        ingredientRecipeJoin
            .on(builder.equal(ingredientRecipeJoin.get(Recipe_.id), recipeId));
    }
    
}
