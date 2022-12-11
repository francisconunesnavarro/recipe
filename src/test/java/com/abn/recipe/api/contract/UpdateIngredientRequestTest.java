package com.abn.recipe.api.contract;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.utils.asserts.IngredientAssert;
import com.abn.recipe.utils.data.IngredientDataProvider;
import com.abn.recipe.utils.data.RecipeDataProvider;

public class UpdateIngredientRequestTest {
    
    @Test
    void toEntityWithId() {
        // Given
        final UUID id = IngredientDataProvider.ID;
        final UpdateIngredientRequest updateIngredientRequest = IngredientDataProvider.createUpdateIngredientRequest();
        
        // When
        final Ingredient ingredient = updateIngredientRequest.toEntity(id);
        
        // Then
        IngredientAssert.assertIngredient(updateIngredientRequest, ingredient, false);
    }
    
    @Test
    void toEntityWithRecipe() {
        // Given
        final UpdateIngredientRequest updateIngredientRequest = IngredientDataProvider.createUpdateIngredientRequest();
        final Recipe recipe = RecipeDataProvider.createRecipe();
        
        // When
        final Ingredient ingredient = updateIngredientRequest.toEntity(recipe);
        
        // Then
        IngredientAssert.assertIngredient(updateIngredientRequest, ingredient, true);
    }
}
