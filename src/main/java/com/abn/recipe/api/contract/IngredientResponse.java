package com.abn.recipe.api.contract;

import java.util.List;
import java.util.UUID;

import com.abn.recipe.infrastructure.entity.Ingredient;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record IngredientResponse(
    UUID id,
    boolean isActive,
    String name,
    double quantity,
    String unitMeasurement
) {
    public static IngredientResponse toResponse(@NonNull final Ingredient ingredient) {
        return new IngredientResponseBuilder()
            .id(ingredient.getId())
            .isActive(ingredient.isActive())
            .name(ingredient.getName())
            .quantity(ingredient.getQuantity())
            .unitMeasurement(ingredient.getUnitMeasurement())
            .build();
    }
    
    public static List<IngredientResponse> toResponse(@NonNull final List<Ingredient> ingredients) {
        return ingredients
            .stream()
            .map(IngredientResponse::toResponse)
            .toList();
    }
}
