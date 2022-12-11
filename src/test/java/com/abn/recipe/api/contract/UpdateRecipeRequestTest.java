package com.abn.recipe.api.contract;

import org.junit.jupiter.api.Test;

import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.utils.asserts.RecipeAssert;
import com.abn.recipe.utils.data.RecipeDataProvider;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateRecipeRequestTest {
    
    @Test
    void toEntity() {
        // Given
        final UpdateRecipeRequest updateRecipeRequest = RecipeDataProvider.createUpdateRecipeRequest();
        
        // When
        final Recipe recipe = updateRecipeRequest.toEntity(RecipeDataProvider.ID);
        
        // Then
        RecipeAssert.assertRecipe(updateRecipeRequest, recipe);
        assertEquals(RecipeDataProvider.ID, recipe.getId());
    }
}
