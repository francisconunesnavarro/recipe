package com.abn.recipe.api.contract;

import org.junit.jupiter.api.Test;

import com.abn.recipe.domain.model.RecipeQuery;
import com.abn.recipe.utils.asserts.RecipeAssert;
import com.abn.recipe.utils.data.RecipeDataProvider;

public class RecipeQueryRequestTest {
    
    @Test
    void toModel() {
        // Given
        final RecipeQueryRequest recipeQueryRequest = RecipeDataProvider.createRecipeQueryRequest();
        
        // When
        final RecipeQuery recipeQuery = recipeQueryRequest.toModel();
        
        // Then
        RecipeAssert.assertRecipe(recipeQueryRequest, recipeQuery);
    }
}
