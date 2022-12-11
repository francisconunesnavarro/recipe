package com.abn.recipe.api.contract;

import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record CreateIngredientRequest(
    boolean isActive,
    String name,
    double quantity,
    String unitMeasurement
) {
    public Ingredient toEntity() {
        final Ingredient ingredient = new Ingredient();
        ingredient.setActive(isActive());
        ingredient.setName(name());
        ingredient.setQuantity(quantity());
        ingredient.setUnitMeasurement(unitMeasurement());
        return ingredient;
    }
    
    public Ingredient toEntity(@NonNull final Recipe recipe) {
        final Ingredient ingredient = toEntity();
        ingredient.setRecipe(recipe);
        return ingredient;
    }
}
