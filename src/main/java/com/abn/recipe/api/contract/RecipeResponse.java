package com.abn.recipe.api.contract;

import java.util.List;
import java.util.UUID;

import com.abn.recipe.infrastructure.entity.Recipe;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record RecipeResponse(
    String cookTime,
    UUID id,
    List<IngredientResponse> ingredients,
    String instructions,
    boolean isActive,
    MealType mealType,
    String name,
    int numberOfServings,
    String prepTime
) {
    public static RecipeResponse toResponse(@NonNull final Recipe recipe) {
        return new RecipeResponseBuilder()
            .cookTime(recipe.getCookTime())
            .id(recipe.getId())
            .ingredients(IngredientResponse.toResponse(recipe.getIngredients()))
            .instructions(recipe.getInstructions())
            .isActive(recipe.isActive())
            .mealType(MealType.valueOf(recipe.getMealType().name()))
            .name(recipe.getName())
            .numberOfServings(recipe.getNumberOfServings())
            .prepTime(recipe.getPrepTime())
            .build();
    }
    
    public static List<RecipeResponse> toResponse(@NonNull final List<Recipe> recipes) {
        return recipes
            .stream()
            .map(RecipeResponse::toResponse)
            .toList();
    }
}
