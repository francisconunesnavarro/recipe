package com.abn.recipe.api.contract;

import java.util.UUID;

import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record UpdateIngredientRequest(
    UUID id,
    boolean isActive,
    String name,
    double quantity,
    String unitMeasurement
) {
    public Ingredient toEntity(@NonNull final UUID id) {
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setActive(isActive());
        ingredient.setName(name());
        ingredient.setQuantity(quantity());
        ingredient.setUnitMeasurement(unitMeasurement());
        return ingredient;
    }
    
    public Ingredient toEntity(@NonNull final Recipe recipe) {
        final Ingredient ingredient = toEntity(id());
        ingredient.setRecipe(recipe);
        return ingredient;
    }
}
