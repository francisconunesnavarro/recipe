package com.abn.recipe.domain.model;

import java.util.Optional;

import com.abn.recipe.infrastructure.entity.MealType;

public record RecipeQuery(
    Optional<Boolean> isExcludeIngredient,
    Optional<MealType> mealType,
    Optional<Integer> numberOfServings,
    Optional<String> searchIngredientName,
    Optional<String> searchInstructions
) {
}
