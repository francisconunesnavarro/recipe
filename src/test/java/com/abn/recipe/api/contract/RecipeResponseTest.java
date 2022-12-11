package com.abn.recipe.api.contract;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.abn.recipe.infrastructure.entity.MealType;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.utils.asserts.RecipeAssert;
import com.abn.recipe.utils.data.RecipeDataProvider;

public class RecipeResponseTest {
    
    @Test
    void toResponse() {
        // Given
        final Recipe recipe = RecipeDataProvider.createRecipe();
        
        // When
        final RecipeResponse recipeResponse = RecipeResponse.toResponse(recipe);
        
        // Then
        RecipeAssert.assertRecipe(recipe, recipeResponse);
    }
    
    @Test
    void toResponseList() {
        // Given
        final Recipe firstRecipe = RecipeDataProvider.createRecipe();
        final Recipe secondRecipe = RecipeDataProvider.createRecipe(
            "10 min",
            "some instructions",
            false, MealType.VEGAN,
            "newRecipe",
            20,
            "40 min"
        );
        final List<Recipe> recipes = List.of(firstRecipe, secondRecipe);
        
        // When
        final List<RecipeResponse> ingredientsResponse = RecipeResponse.toResponse(recipes);
        
        // Then
        for (int i = 0; i < ingredientsResponse.size(); i++) {
            RecipeAssert.assertRecipe(recipes.get(i), ingredientsResponse.get(i));
        }
    }
}
