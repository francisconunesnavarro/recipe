package com.abn.recipe.api.contract;

import java.util.List;

import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CreateRecipeRequest(
    String cookTime,
    List<CreateIngredientRequest> ingredients,
    String instructions,
    boolean isActive,
    MealType mealType,
    String name,
    int numberOfServings,
    String prepTime
) {
    public Recipe toEntity() {
        final Recipe recipe = new Recipe();
        recipe.setCookTime(cookTime());
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
            .map(createIngredientRequest -> createIngredientRequest.toEntity(recipe))
            .toList();
    }
}
