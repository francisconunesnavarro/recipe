package com.abn.recipe.api.contract;

import java.util.Optional;

import com.abn.recipe.domain.model.RecipeQuery;

public record RecipeQueryRequest(
    Optional<Boolean> isExcludeIngredient,
    Optional<MealType> mealType,
    Optional<Integer> numberOfServings,
    Optional<String> searchIngredientName,
    Optional<String> searchInstructions
) {
    public RecipeQuery toModel() {
        return new RecipeQuery(
            Optional.of(isExcludeIngredient()
                .orElse(false)),
            mealType()
                .map(mealTypeRequest -> com.abn.recipe.infrastructure.entity.MealType.valueOf(mealTypeRequest.name())),
            numberOfServings(),
            searchIngredientName(),
            searchInstructions()
        );
    }
}
