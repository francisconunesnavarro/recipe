package com.abn.recipe.domain.model;

import java.util.Optional;
import java.util.UUID;

public record IngredientQuery(
    Optional<UUID> recipeId
) {
}
