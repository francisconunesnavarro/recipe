package com.abn.recipe.api.contract;

import org.junit.jupiter.api.Test;

import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.utils.asserts.IngredientAssert;
import com.abn.recipe.utils.data.IngredientDataProvider;
import com.abn.recipe.utils.data.RecipeDataProvider;

public class CreateIngredientRequestTest {
    
    @Test
    void toEntity() {
        // Given
        final CreateIngredientRequest createIngredientRequest = IngredientDataProvider.createIngredientRequest();
        
        // When
        final Ingredient ingredient = createIngredientRequest.toEntity();
        
        // Then
        IngredientAssert.assertIngredient(createIngredientRequest, ingredient, false);
    }
    
    @Test
    void toEntityWithRecipe() {
        // Given
        final CreateIngredientRequest createIngredientRequest = IngredientDataProvider.createIngredientRequest();
        final Recipe recipe = RecipeDataProvider.createRecipe();
        
        // When
        final Ingredient ingredient = createIngredientRequest.toEntity(recipe);
        
        // Then
        IngredientAssert.assertIngredient(createIngredientRequest, ingredient, true);
    }
}
