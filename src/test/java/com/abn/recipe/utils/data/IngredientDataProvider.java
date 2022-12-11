package com.abn.recipe.utils.data;

import java.util.UUID;

import com.abn.recipe.api.contract.CreateIngredientRequest;
import com.abn.recipe.api.contract.UpdateIngredientRequest;
import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;

import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
public class IngredientDataProvider {
    public static UUID ID = UUID.randomUUID();
    public static String NAME = "ingredientName";
    public static String UNIT_MEASUREMENT = "unitMeasurement";
    public static boolean IS_ACTIVE = true;
    public static double QUANTITY = 2.0;
    
    public static Ingredient createIngredient(
        final boolean isActive,
        final String name,
        final double quantity,
        final String unitMeasurement,
        final Recipe recipe
    ) {
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setActive(isActive);
        ingredient.setName(name);
        ingredient.setRecipe(recipe);
        ingredient.setQuantity(quantity);
        ingredient.setUnitMeasurement(unitMeasurement);
        return ingredient;
    }
    
    public static Ingredient createIngredient(
        final boolean isActive,
        final String name,
        final double quantity,
        final String unitMeasurement
    ) {
        return createIngredient(isActive, name, quantity, unitMeasurement, null);
    }
    
    public static Ingredient createIngredient() {
        return createIngredient(IS_ACTIVE, NAME, QUANTITY, UNIT_MEASUREMENT);
    }
    
    public static Ingredient createIngredient(final Recipe recipe) {
        return createIngredient(IS_ACTIVE, NAME, QUANTITY, UNIT_MEASUREMENT, recipe);
    }
    
    public static CreateIngredientRequest createIngredientRequest() {
        return new CreateIngredientRequest(IS_ACTIVE, NAME, QUANTITY, UNIT_MEASUREMENT);
    }
    
    public static UpdateIngredientRequest createUpdateIngredientRequest() {
        return new UpdateIngredientRequest(ID, IS_ACTIVE, NAME, QUANTITY, UNIT_MEASUREMENT);
    }
    
    public static UpdateIngredientRequest createUpdateIngredientRequest(final UUID id) {
        return new UpdateIngredientRequest(id, IS_ACTIVE, NAME, QUANTITY, UNIT_MEASUREMENT);
    }
}
