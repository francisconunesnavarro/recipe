package com.abn.recipe.infrastructure.entity;

import org.junit.jupiter.api.Test;

import com.abn.recipe.utils.data.RecipeDataProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecipeTest {
    
    @Test
    void update() {
        // Given
        final Recipe recipe = RecipeDataProvider.createRecipe();
        final Recipe newrecipe = RecipeDataProvider.createRecipe(
            "10 min",
            "some instructions",
            false, MealType.VEGAN,
            "newRecipe",
            20,
            "40 min"
        );
        
        // When
        final Recipe recipeUpdated = recipe.update(newrecipe);
        
        // Then
        assertEquals(newrecipe, recipeUpdated);
    }
}
