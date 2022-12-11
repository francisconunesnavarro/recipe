package com.abn.recipe.api.contract;

import java.util.List;
import java.util.UUID;

import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record UpdateRecipeRequest(
    String cookTime,
    List<UpdateIngredientRequest> ingredients,
    String instructions,
    boolean isActive,
    MealType mealType,
    String name,
    int numberOfServings,
    String prepTime
) {
    public Recipe toEntity(@NonNull final UUID id) {
        final Recipe recipe = new Recipe();
        recipe.setCookTime(cookTime());
        recipe.setId(id);
        recipe.setIngredients(addIngredients(recipe));
        recipe.setInstructions(instructions());
        recipe.setActive(isActive());
        recipe.setMealType(com.abn.recipe.infrastructure.entity.MealType.valueOf(mealType().name()));
        recipe.setName(name());
        recipe.setNumberOfServings(numberOfServings());
        recipe.setPrepTime(prepTime());
        return recipe;
    }
    
    private List<Ingredient> addIngredients(@NonNull final Recipe recipe) {
        return ingredients()
            .stream()
            .map(updateIngredientRequest -> updateIngredientRequest.toEntity(recipe))
            .toList();
    }
}
