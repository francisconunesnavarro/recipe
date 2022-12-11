package com.abn.recipe.utils.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.abn.recipe.api.contract.CreateRecipeRequest;
import com.abn.recipe.api.contract.RecipeQueryRequest;
import com.abn.recipe.api.contract.UpdateRecipeRequest;
import com.abn.recipe.domain.model.RecipeQuery;
import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.MealType;
import com.abn.recipe.infrastructure.entity.Recipe;

import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
public class RecipeDataProvider {
    public static UUID ID = UUID.randomUUID();
    public static String COOK_TIME = "cookTime";
    public static String INSTRUCTIONS = "instructions";
    public static String NAME = "recipeName";
    public static String PREP_TIME = "prepTime";
    public static String SEARCH_INGREDIENT_NAME = "searchIngredientName";
    public static String SEARCH_INSTRUCTION = "searchInstructions";
    public static MealType MEAL_TYPE = MealType.FISH;
    public static boolean IS_ACTIVE = true;
    public static int NUMBER_OF_SERVINGS = 4;
    
    public static Recipe createRecipe(
        final String cookTime,
        final String instructions,
        final boolean isActive,
        final MealType mealType,
        final String name,
        final int numberOfServings,
        final String prepTime
    ) {
        final Recipe recipe = new Recipe();
        
        final Ingredient firstIngredientMock = IngredientDataProvider.createIngredient(recipe);
        final Ingredient secondIngredientMock = IngredientDataProvider.createIngredient(
            false,
            "newIngredient",
            200.0,
            "g",
            recipe
        );
        final List<Ingredient> ingredients = List.of(firstIngredientMock, secondIngredientMock);
        
        recipe.setCookTime(cookTime);
        recipe.setId(ID);
        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);
        recipe.setActive(isActive);
        recipe.setMealType(mealType);
        recipe.setName(name);
        recipe.setNumberOfServings(numberOfServings);
        recipe.setPrepTime(prepTime);
        return recipe;
    }
    
    public static Recipe createRecipe() {
        return createRecipe(COOK_TIME, INSTRUCTIONS, IS_ACTIVE, MEAL_TYPE, NAME, NUMBER_OF_SERVINGS, PREP_TIME);
    }
    
    public static RecipeQuery createRecipeQuery() {
        return new RecipeQuery(
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
        );
    }
    
    public static CreateRecipeRequest createRecipeRequest() {
        return new CreateRecipeRequest(
            COOK_TIME,
            List.of(IngredientDataProvider.createIngredientRequest()),
            INSTRUCTIONS,
            IS_ACTIVE,
            com.abn.recipe.api.contract.MealType.MEAT,
            NAME,
            NUMBER_OF_SERVINGS,
            PREP_TIME
        );
    }
    
    public static UpdateRecipeRequest createUpdateRecipeRequest(final UUID ingredientId) {
        return new UpdateRecipeRequest(
            COOK_TIME,
            List.of(IngredientDataProvider.createUpdateIngredientRequest(ingredientId)),
            INSTRUCTIONS,
            IS_ACTIVE,
            com.abn.recipe.api.contract.MealType.MEAT,
            NAME,
            NUMBER_OF_SERVINGS,
            PREP_TIME
        );
    }
    
    public static UpdateRecipeRequest createUpdateRecipeRequest() {
        return createUpdateRecipeRequest(IngredientDataProvider.ID);
    }
    
    public static RecipeQueryRequest createRecipeQueryRequest() {
        return new RecipeQueryRequest(
            Optional.of(true),
            Optional.of(com.abn.recipe.api.contract.MealType.MEAT),
            Optional.of(NUMBER_OF_SERVINGS),
            Optional.of(SEARCH_INGREDIENT_NAME),
            Optional.of(SEARCH_INSTRUCTION)
        );
    }
}
