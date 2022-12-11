package com.abn.recipe.infrastructure.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import com.abn.recipe.domain.model.RecipeQuery;
import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Ingredient_;
import com.abn.recipe.infrastructure.entity.MealType;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.infrastructure.entity.Recipe_;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

/**
 * Specification for Recipe.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecipeSpecification implements Specification<Recipe> {
    
    RecipeQuery recipeQuery;
    static String CHARACTER_LIKE = "%";
    
    public RecipeSpecification(@NonNull final RecipeQuery recipeQuery) {
        this.recipeQuery = recipeQuery;
    }
    
    @Override
    public Predicate toPredicate(
        @NonNull final Root<Recipe> root,
        @NonNull final CriteriaQuery<?> query,
        @NonNull final CriteriaBuilder builder
    ) {
        return builder.and(
            assembleWhere(root, query, builder).toArray(new Predicate[0]));
    }
    
    private List<Predicate> assembleWhere(
        @NonNull final Root<Recipe> root,
        @NonNull final CriteriaQuery<?> query,
        @NonNull final CriteriaBuilder builder
    ) {
        final List<Predicate> predicates = new ArrayList<>();
        
        appendIngredientName(root, query, builder)
            .ifPresent(predicates::add);
        appendInstruction(root, builder)
            .ifPresent(predicates::add);
        appendMealType(root, builder)
            .ifPresent(predicates::add);
        appendNumberOfServings(root, builder)
            .ifPresent(predicates::add);
        
        return predicates;
    }
    
    private Optional<Predicate> appendIngredientName(
        @NonNull final Root<Recipe> root,
        @NonNull final CriteriaQuery<?> query,
        @NonNull final CriteriaBuilder builder
    ) {
        return recipeQuery
            .searchIngredientName()
            .flatMap(ingredientName -> recipeQuery
                .isExcludeIngredient()
                .map(isExcludeIngredient -> appendExistIngredient(root, query, builder, ingredientName, isExcludeIngredient))
            );
        
    }
    
    private Predicate appendExistIngredient(
        @NonNull final Root<Recipe> root,
        @NonNull final CriteriaQuery<?> query,
        @NonNull final CriteriaBuilder builder,
        @NonNull final String ingredientName,
        @NonNull final Boolean isExcludeIngredient
    ) {
        final Subquery<Ingredient> subQuery = query.subquery(Ingredient.class);
        final Root<Ingredient> subRoot = subQuery.from(Ingredient.class);
        
        subQuery
            .select(subRoot)
            .where(builder.and(
                builder.equal(root, subRoot.get(Ingredient_.recipe)),
                builder.like(
                    builder.lower(SpecificationNormalize.normalizeDbColumn(builder, subRoot.get(Ingredient_.name))),
                    CHARACTER_LIKE + SpecificationNormalize.normalizeValue(ingredientName.trim().toLowerCase()) + CHARACTER_LIKE
                )
            ));
        
        return isExcludeIngredient
               ? builder.not(builder.exists(subQuery))
               : builder.exists(subQuery);
    }
    
    private Optional<Predicate> appendInstruction(
        @NonNull final Root<Recipe> root,
        @NonNull final CriteriaBuilder builder
    ) {
        return recipeQuery
            .searchInstructions()
            .map(instructions -> builder
                .like(builder.lower(SpecificationNormalize.normalizeDbColumn(builder, root.get(Recipe_.instructions))),
                    CHARACTER_LIKE + SpecificationNormalize.normalizeValue(instructions.trim().toLowerCase()) + CHARACTER_LIKE
                ));
    }
    
    private Optional<Predicate> appendMealType(
        @NonNull final Root<Recipe> root,
        @NonNull final CriteriaBuilder builder
    ) {
        return recipeQuery
            .mealType()
            .map(mealType -> builder.equal(root.get(Recipe_.mealType), MealType.valueOf(mealType.name())));
    }
    
    private Optional<Predicate> appendNumberOfServings(
        @NonNull final Root<Recipe> root,
        @NonNull final CriteriaBuilder builder
    ) {
        return recipeQuery
            .numberOfServings()
            .map(numberOfServings -> builder.greaterThanOrEqualTo(root.get(Recipe_.numberOfServings), numberOfServings));
    }
}
