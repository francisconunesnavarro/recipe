package com.abn.recipe.api.contract;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.utils.asserts.IngredientAssert;
import com.abn.recipe.utils.data.IngredientDataProvider;

public class IngredientResponseTest {
    
    @Test
    void toResponse() {
        // Given
        final Ingredient ingredient = IngredientDataProvider.createIngredient();
        
        // When
        final IngredientResponse ingredientResponse = IngredientResponse.toResponse(ingredient);
        
        // Then
        IngredientAssert.assertIngredient(ingredient, ingredientResponse);
    }
    
    @Test
    void toResponseList() {
        // Given
        final Ingredient firstIngredient = IngredientDataProvider.createIngredient();
        final Ingredient secondIngredient = IngredientDataProvider.createIngredient(false, "newIngredient", 200.0, "g");
        final List<Ingredient> ingredients = List.of(firstIngredient, secondIngredient);
        
        // When
        final List<IngredientResponse> ingredientsResponse = IngredientResponse.toResponse(ingredients);
        
        // Then
        for (int i = 0; i < ingredientsResponse.size(); i++) {
            IngredientAssert.assertIngredient(ingredients.get(i), ingredientsResponse.get(i));
        }
    }
}
