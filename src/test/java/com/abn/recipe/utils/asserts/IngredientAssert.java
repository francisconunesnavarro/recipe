package com.abn.recipe.utils.asserts;

import com.abn.recipe.api.contract.CreateIngredientRequest;
import com.abn.recipe.api.contract.IngredientResponse;
import com.abn.recipe.api.contract.UpdateIngredientRequest;
import com.abn.recipe.infrastructure.entity.Ingredient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public final class IngredientAssert {
    
    public static void assertIngredient(
        final CreateIngredientRequest expected,
        final Ingredient actual,
        final boolean hasRecipe
    ) {
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.quantity(), actual.getQuantity());
        assertEquals(expected.unitMeasurement(), actual.getUnitMeasurement());
        
        if (hasRecipe) {
            assertNotNull(actual.getRecipe());
        } else {
            assertNull(actual.getRecipe());
        }
    }
    
    public static void assertIngredient(
        final CreateIngredientRequest expected,
        final IngredientResponse actual
    ) {
        assertNotNull(actual.id());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.quantity(), actual.quantity());
        assertEquals(expected.unitMeasurement(), actual.unitMeasurement());
    }
    
    public static void assertIngredient(
        final UpdateIngredientRequest expected,
        final Ingredient actual,
        final boolean hasRecipe
    ) {
        assertEquals(expected.id(), actual.getId());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.name(), actual.getName());
        assertEquals(expected.quantity(), actual.getQuantity());
        assertEquals(expected.unitMeasurement(), actual.getUnitMeasurement());
        
        if (hasRecipe) {
            assertNotNull(actual.getRecipe());
        } else {
            assertNull(actual.getRecipe());
        }
    }
    
    public static void assertIngredient(
        final UpdateIngredientRequest expected,
        final IngredientResponse actual
    ) {
        assertEquals(expected.id(), actual.id());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.quantity(), actual.quantity());
        assertEquals(expected.unitMeasurement(), actual.unitMeasurement());
    }
    
    public static void assertIngredient(
        final Ingredient expected,
        final IngredientResponse actual
    ) {
        assertEquals(expected.getId(), actual.id());
        assertEquals(expected.isActive(), actual.isActive());
        assertEquals(expected.getName(), actual.name());
        assertEquals(expected.getQuantity(), actual.quantity());
        assertEquals(expected.getUnitMeasurement(), actual.unitMeasurement());
    }
}
