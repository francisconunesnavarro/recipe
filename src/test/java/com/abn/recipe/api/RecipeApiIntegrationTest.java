package com.abn.recipe.api;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.abn.recipe.api.contract.CreateRecipeRequest;
import com.abn.recipe.api.contract.MealType;
import com.abn.recipe.api.contract.RecipeResponse;
import com.abn.recipe.api.contract.UpdateRecipeRequest;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.infrastructure.repository.RecipeRepository;
import com.abn.recipe.utils.asserts.RecipeAssert;
import com.abn.recipe.utils.data.RecipeDataProvider;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipeApiIntegrationTest extends GenericApiIntegrationTest {
    
    static String RECIPE_URL = "/recipes";
    static String UTF_CHARSET = "UTF-8";
    static String QUERY_PARAM_IS_EXCLUDE_INGREDIENT = "isExcludeIngredient";
    static String QUERY_PARAM_MEAL_TYPE = "mealType";
    static String QUERY_PARAM_NUMBER_OF_SERVINGS = "numberOfServings";
    static String QUERY_PARAM_SEARCH_INGREDIENT_NAME = "searchIngredientName";
    static String QUERY_PARAM_SEARCH_INSTRUCTIONS = "searchInstructions";
    static UUID RECIPE_ID = UUID.fromString("700d01de-8fb0-46d2-abdb-6fea0eb81693");
    static UUID RECIPE_ID_DELETE = UUID.fromString("0823e72d-2934-4968-991e-f98dc1e073dc");
    static UUID INGREDIENT_ID_UPDATE = UUID.fromString("b414b925-723a-488d-a590-9f4699fcfdaa");
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    RecipeRepository recipeRepository;
    
    @Test
    void findAll() throws Exception {
        // When
        final ResultActions result = mockMvc
            .perform(get(RECIPE_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<RecipeResponse> recipesResponse = Arrays.asList(extractEntity(result.andReturn(), RecipeResponse[].class));
        
        // Then
        final List<Recipe> recipes = recipeRepository.findAll();
        
        assertFalse(recipes.isEmpty());
        assertEquals(recipes.size(), recipesResponse.size());
    }
    
    @Test
    void findAllByExcludeIngredientTrue() throws Exception {
        // Given
        final String url = RECIPE_URL;
        final String excludeIngredient = "true";
        
        // When
        final ResultActions result = mockMvc
            .perform(get(url)
                .param(QUERY_PARAM_IS_EXCLUDE_INGREDIENT, excludeIngredient))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<RecipeResponse> recipesResponse = Arrays.asList(extractEntity(result.andReturn(), RecipeResponse[].class));
        
        // Then
        final List<Recipe> recipes = recipeRepository.findAll();
        
        assertFalse(recipes.isEmpty());
        assertEquals(recipes.size(), recipesResponse.size());
    }
    
    @Test
    void findAllByExcludeIngredientFalse() throws Exception {
        // Given
        final String url = RECIPE_URL;
        final String excludeIngredient = "false";
        
        // When
        final ResultActions result = mockMvc
            .perform(get(url)
                .param(QUERY_PARAM_IS_EXCLUDE_INGREDIENT, excludeIngredient))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<RecipeResponse> recipesResponse = Arrays.asList(extractEntity(result.andReturn(), RecipeResponse[].class));
        
        // Then
        final List<Recipe> recipes = recipeRepository.findAll();
        
        assertFalse(recipes.isEmpty());
        assertEquals(recipes.size(), recipesResponse.size());
    }
    
    @Test
    void findAllByMealType() throws Exception {
        // Given
        final String url = RECIPE_URL;
        final String mealType = MealType.VEGAN.name();
        
        // When
        final ResultActions result = mockMvc
            .perform(get(url)
                .param(QUERY_PARAM_MEAL_TYPE, mealType))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<RecipeResponse> recipesResponse = Arrays.asList(extractEntity(result.andReturn(), RecipeResponse[].class));
        
        // Then
        final List<Recipe> recipes = recipeRepository.findAll()
            .stream()
            .filter(recipe -> mealType.equals(recipe.getMealType().name()))
            .toList();
        
        assertFalse(recipes.isEmpty());
        assertEquals(recipes.size(), recipesResponse.size());
    }
    
    @Test
    void findAllByNumberOfServings() throws Exception {
        // Given
        final String url = RECIPE_URL;
        final int numberOfServings = 6;
        
        // When
        final ResultActions result = mockMvc
            .perform(get(url)
                .param(QUERY_PARAM_NUMBER_OF_SERVINGS, Integer.toString(numberOfServings)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<RecipeResponse> recipesResponse = Arrays.asList(extractEntity(result.andReturn(), RecipeResponse[].class));
        
        // Then
        final List<Recipe> recipes = recipeRepository.findAll()
            .stream()
            .filter(recipe -> recipe.getNumberOfServings() >= numberOfServings)
            .toList();
        
        assertFalse(recipes.isEmpty());
        assertEquals(recipes.size(), recipesResponse.size());
    }
    
    @Transactional
    @Test
    void findAllByIngredientName() throws Exception {
        // Given
        final String url = RECIPE_URL;
        final String ingredientName = "Garlic";
        
        // When
        final ResultActions result = mockMvc
            .perform(get(url)
                .param(QUERY_PARAM_SEARCH_INGREDIENT_NAME, ingredientName))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<RecipeResponse> recipesResponse = Arrays.asList(extractEntity(result.andReturn(), RecipeResponse[].class));
        
        // Then
        final List<Recipe> recipes = recipeRepository.findAll()
            .stream()
            .filter(recipe -> recipe
                .getIngredients()
                .stream()
                .anyMatch(ingredient -> ingredient.getName().toLowerCase().contains(ingredientName.toLowerCase())))
            .toList();
        
        assertFalse(recipes.isEmpty());
        assertEquals(recipes.size(), recipesResponse.size());
    }
    
    @Transactional
    @Test
    void findAllByIngredientNameAndExcludeIngredientTrue() throws Exception {
        // Given
        final String url = RECIPE_URL;
        final String ingredientName = "Garlic";
        final String excludeIngredient = "true";
        
        // When
        final ResultActions result = mockMvc
            .perform(get(url)
                .param(QUERY_PARAM_SEARCH_INGREDIENT_NAME, ingredientName)
                .param(QUERY_PARAM_IS_EXCLUDE_INGREDIENT, excludeIngredient))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<RecipeResponse> recipesResponse = Arrays.asList(extractEntity(result.andReturn(), RecipeResponse[].class));
        
        // Then
        final List<Recipe> recipes = recipeRepository.findAll();
        final List<Recipe> recipesWithIngredient = recipes
            .stream()
            .filter(recipe -> recipe
                .getIngredients()
                .stream()
                .anyMatch(ingredient -> ingredient.getName().toLowerCase().contains(ingredientName.toLowerCase())))
            .toList();
        recipes
            .removeIf(recipe -> recipesWithIngredient
                .stream()
                .anyMatch(recipeWithIngredient -> recipeWithIngredient.getId().equals(recipe.getId())));
        
        assertFalse(recipes.isEmpty());
        assertEquals(recipes.size(), recipesResponse.size());
    }
    
    @Test
    void findAllByInstructions() throws Exception {
        // Given
        final String url = RECIPE_URL;
        final String instructions = "pan";
        
        // When
        final ResultActions result = mockMvc
            .perform(get(url)
                .param(QUERY_PARAM_SEARCH_INSTRUCTIONS, instructions))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<RecipeResponse> recipesResponse = Arrays.asList(extractEntity(result.andReturn(), RecipeResponse[].class));
        
        // Then
        final List<Recipe> recipes = recipeRepository.findAll()
            .stream()
            .filter(recipe -> recipe.getInstructions().toLowerCase().contains(instructions.toLowerCase()))
            .toList();
        
        assertFalse(recipes.isEmpty());
        assertEquals(recipes.size(), recipesResponse.size());
    }
    
    @Transactional
    @Test
    void create() throws Exception {
        // Given
        final CreateRecipeRequest createRecipeRequest = RecipeDataProvider.createRecipeRequest();
        
        // When
        final ResultActions result = mockMvc
            .perform(post(RECIPE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createRecipeRequest))
                .characterEncoding(UTF_CHARSET))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final RecipeResponse recipeResponse = extractEntity(result.andReturn(), RecipeResponse.class);
        
        // Then
        final Optional<Recipe> recipe = recipeRepository.findById(recipeResponse.id());
        assertTrue(recipe.isPresent());
        RecipeAssert.assertRecipe(recipe.get(), recipeResponse);
        RecipeAssert.assertRecipe(createRecipeRequest, recipeResponse);
    }
    
    @Transactional
    @Test
    void update() throws Exception {
        // Given
        final String url = RECIPE_URL + "/" + RECIPE_ID;
        final UpdateRecipeRequest updateRecipeRequest = RecipeDataProvider.createUpdateRecipeRequest(INGREDIENT_ID_UPDATE);
        
        // When
        final ResultActions result = mockMvc
            .perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateRecipeRequest))
                .characterEncoding(UTF_CHARSET))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final RecipeResponse recipeResponse = extractEntity(result.andReturn(), RecipeResponse.class);
        
        // Then
        final Optional<Recipe> recipe = recipeRepository.findById(RECIPE_ID);
        assertTrue(recipe.isPresent());
        RecipeAssert.assertRecipe(recipe.get(), recipeResponse);
        RecipeAssert.assertRecipe(updateRecipeRequest, recipeResponse);
    }
    
    @Test
    void updateFailWhenIngredientIdNotExists() throws Exception {
        // Given
        final UUID id = UUID.randomUUID();
        final String url = RECIPE_URL + "/" + id;
        final UpdateRecipeRequest updateRecipeRequest = RecipeDataProvider.createUpdateRecipeRequest(id);
        
        // When
        final ResultActions result = mockMvc
            .perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateRecipeRequest))
                .characterEncoding(UTF_CHARSET));
        
        // Then
        result.andExpect(status().isNotFound());
    }
    
    @Test
    void delete() throws Exception {
        // Given
        final String url = RECIPE_URL + "/" + RECIPE_ID_DELETE;
        
        // When
        mockMvc
            .perform(MockMvcRequestBuilders.delete(url))
            .andExpect(status().isNoContent())
            .andReturn();
        
        // Then
        final Optional<Recipe> recipe = recipeRepository.findById(RECIPE_ID_DELETE);
        
        assertTrue(recipe.isEmpty());
    }
    
    @Test
    void deleteFailWhenRecipeIdNotExists() throws Exception {
        // Given
        final String url = RECIPE_URL + "/" + UUID.randomUUID();
        
        // When
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(url));
        
        // Then
        result.andExpect(status().isNotFound());
    }
}
