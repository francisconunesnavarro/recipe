package com.abn.recipe.infrastructure.entity;

import org.junit.jupiter.api.Test;

import com.abn.recipe.utils.data.IngredientDataProvider;
import com.abn.recipe.utils.data.RecipeDataProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IngredientTest {
    
    @Test
    void addRecipe() {
        // Given
        final Ingredient ingredient = IngredientDataProvider.createIngredient();
        final Recipe recipe = RecipeDataProvider.createRecipe();
        
        // When
        final Ingredient ingredientUpdated = ingredient.addRecipe(recipe);
        
        // Then
        assertEquals(recipe, ingredientUpdated.getRecipe());
    }
    
    @Test
    void update() {
        // Given
        final Ingredient ingredient = IngredientDataProvider.createIngredient();
        final Ingredient newIngredient = IngredientDataProvider.createIngredient(false, "newIngredient", 200.0, "g");
        
        // When
        final Ingredient ingredientUpdated = ingredient.update(newIngredient);
        
        // Then
        assertEquals(newIngredient, ingredientUpdated);
    }
}
