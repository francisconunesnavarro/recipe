package com.abn.recipe.utils.asserts;

import com.abn.recipe.api.contract.CreateRecipeRequest;
import com.abn.recipe.api.contract.RecipeQueryRequest;
import com.abn.recipe.api.contract.RecipeResponse;
import com.abn.recipe.api.contract.UpdateRecipeRequest;
import com.abn.recipe.domain.model.RecipeQuery;
import com.abn.recipe.infrastructure.entity.Recipe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public final class RecipeAssert {
    
    public static void assertRecipe(
        final CreateRecipeRequest expected,
        final Recipe actual
    ) {
        assertEquals(expected.cookTime(), actual.getCookTime());
        assertEquals(expected.instructions(), actual.getInstructions());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.mealType().name(), actual.getMealType().name());
        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.numberOfServings(), actual.getNumberOfServings());
        assertEquals(expected.prepTime(), actual.getPrepTime());
        // Ingredients
        assertEquals(expected.ingredients().size(), actual.getIngredients().size());
        for (int i = 0; i < actual.getIngredients().size(); i++) {
            IngredientAssert.assertIngredient(expected.ingredients().get(i), actual.getIngredients().get(i), true);
        }
    }
    
    public static void assertRecipe(
        final CreateRecipeRequest expected,
        final RecipeResponse actual
    ) {
        assertEquals(expected.cookTime(), actual.cookTime());
        assertNotNull(actual.id());
        assertEquals(expected.instructions(), actual.instructions());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.mealType().name(), actual.mealType().name());
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.numberOfServings(), actual.numberOfServings());
        assertEquals(expected.prepTime(), actual.prepTime());
        // Ingredients
        assertEquals(expected.ingredients().size(), actual.ingredients().size());
        for (int i = 0; i < actual.ingredients().size(); i++) {
            IngredientAssert.assertIngredient(expected.ingredients().get(i), actual.ingredients().get(i));
        }
    }
    
    public static void assertRecipe(
        final UpdateRecipeRequest expected,
        final Recipe actual
    ) {
        assertEquals(expected.cookTime(), actual.getCookTime());
        assertEquals(expected.instructions(), actual.getInstructions());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.mealType().name(), actual.getMealType().name());
        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.numberOfServings(), actual.getNumberOfServings());
        assertEquals(expected.prepTime(), actual.getPrepTime());
        // Ingredients
        assertEquals(expected.ingredients().size(), actual.getIngredients().size());
        for (int i = 0; i < actual.getIngredients().size(); i++) {
            IngredientAssert.assertIngredient(expected.ingredients().get(i), actual.getIngredients().get(i), true);
        }
    }
    
    public static void assertRecipe(
        final UpdateRecipeRequest expected,
        final RecipeResponse actual
    ) {
        assertEquals(expected.cookTime(), actual.cookTime());
        assertNotNull(actual.id());
        assertEquals(expected.instructions(), actual.instructions());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.mealType().name(), actual.mealType().name());
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.numberOfServings(), actual.numberOfServings());
        assertEquals(expected.prepTime(), actual.prepTime());
        // Ingredients
        assertEquals(expected.ingredients().size(), actual.ingredients().size());
        for (int i = 0; i < actual.ingredients().size(); i++) {
            IngredientAssert.assertIngredient(expected.ingredients().get(i), actual.ingredients().get(i));
        }
    }
    
    public static void assertRecipe(
        final Recipe expected,
        final RecipeResponse actual
    ) {
        assertEquals(expected.getCookTime(), actual.cookTime());
        assertEquals(expected.getInstructions(), actual.instructions());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.getMealType().name(), actual.mealType().name());
        assertEquals(expected.getName(), actual.name());
        assertEquals(expected.getNumberOfServings(), actual.numberOfServings());
        assertEquals(expected.getPrepTime(), actual.prepTime());
        // Ingredients
        assertEquals(expected.getIngredients().size(), actual.ingredients().size());
        for (int i = 0; i < actual.ingredients().size(); i++) {
            IngredientAssert.assertIngredient(expected.getIngredients().get(i), actual.ingredients().get(i));
        }
    }
    
    public static void assertRecipe(
        final RecipeQueryRequest expected,
        final RecipeQuery actual
    ) {
        assertEquals(expected.isExcludeIngredient().get(), actual.isExcludeIngredient().get());
        assertEquals(expected.mealType().get().name(), actual.mealType().get().name());
        assertEquals(expected.numberOfServings().get(), actual.numberOfServings().get());
        assertEquals(expected.searchIngredientName().get(), actual.searchIngredientName().get());
        assertEquals(expected.searchInstructions().get(), actual.searchInstructions().get());
    }
}
