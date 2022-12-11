package com.abn.recipe.api.contract;

import org.junit.jupiter.api.Test;

import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.utils.asserts.RecipeAssert;
import com.abn.recipe.utils.data.RecipeDataProvider;

public class CreateRecipeRequestTest {
    
    @Test
    void toEntity() {
        // Given
        final CreateRecipeRequest createIngredientRequest = RecipeDataProvider.createRecipeRequest();
        
        // When
        final Recipe recipe = createIngredientRequest.toEntity();
        
        // Then
        RecipeAssert.assertRecipe(createIngredientRequest, recipe);
    }
}
