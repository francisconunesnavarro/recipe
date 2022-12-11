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

import com.abn.recipe.api.contract.CreateIngredientRequest;
import com.abn.recipe.api.contract.IngredientResponse;
import com.abn.recipe.api.contract.UpdateIngredientRequest;
import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.repository.IngredientRepository;
import com.abn.recipe.utils.asserts.IngredientAssert;
import com.abn.recipe.utils.data.IngredientDataProvider;

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
public class IngredientApiIntegrationTest extends GenericApiIntegrationTest {
    
    static String INGREDIENT_URL = "/ingredients";
    static String RECIPE_URL = "/recipes";
    static String UTF_CHARSET = "UTF-8";
    static UUID RECIPE_ID = UUID.fromString("700d01de-8fb0-46d2-abdb-6fea0eb81693");
    static UUID INGREDIENT_ID = UUID.fromString("1c3b215b-dacd-4296-806a-765da4d1b39a");
    static UUID INGREDIENT_ID_DELETE = UUID.fromString("42f3ab76-be75-47ec-9677-ca13c16537f5");
    
    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    IngredientRepository ingredientRepository;
    
    @Test
    void findAll() throws Exception {
        // When
        final ResultActions result = mockMvc
            .perform(get(INGREDIENT_URL))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<IngredientResponse> ingredientsResponse = Arrays.asList(extractEntity(result.andReturn(), IngredientResponse[].class));
        
        // Then
        final List<Ingredient> ingredients = ingredientRepository.findAll();
        
        assertFalse(ingredients.isEmpty());
        assertEquals(ingredients.size(), ingredientsResponse.size());
    }
    
    @Transactional
    @Test
    void findAllByRecipeId() throws Exception {
        // Given
        final String url = RECIPE_URL + "/" + RECIPE_ID + INGREDIENT_URL;
        
        // When
        final ResultActions result = mockMvc
            .perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<IngredientResponse> ingredientsResponse = Arrays.asList(extractEntity(result.andReturn(), IngredientResponse[].class));
        
        // Then
        final List<Ingredient> ingredients = ingredientRepository.findAll()
            .stream()
            .filter(ingredient -> RECIPE_ID.equals(ingredient.getRecipe().getId()))
            .toList();
        
        assertFalse(ingredients.isEmpty());
        assertEquals(ingredients.size(), ingredientsResponse.size());
    }
    
    @Test
    void findAllByRecipeIdWhenRecipeIdNotExists() throws Exception {
        // Given
        final String url = RECIPE_URL + "/" + UUID.randomUUID() + INGREDIENT_URL;
        
        // When
        final ResultActions result = mockMvc
            .perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final List<IngredientResponse> ingredientsResponse = Arrays.asList(extractEntity(result.andReturn(), IngredientResponse[].class));
        
        // Then
        assertTrue(ingredientsResponse.isEmpty());
    }
    
    @Test
    void create() throws Exception {
        // Given
        final String url = RECIPE_URL + "/" + RECIPE_ID + INGREDIENT_URL;
        final CreateIngredientRequest createIngredientRequest = IngredientDataProvider.createIngredientRequest();
        
        // When
        final ResultActions result = mockMvc
            .perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createIngredientRequest))
                .characterEncoding(UTF_CHARSET))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final IngredientResponse ingredientResponse = extractEntity(result.andReturn(), IngredientResponse.class);
        
        // Then
        final Optional<Ingredient> ingredient = ingredientRepository.findById(ingredientResponse.id());
        assertTrue(ingredient.isPresent());
        IngredientAssert.assertIngredient(ingredient.get(), ingredientResponse);
        IngredientAssert.assertIngredient(createIngredientRequest, ingredientResponse);
    }
    
    @Test
    void createFailWhenRecipeIdNotExists() throws Exception {
        // Given
        final String url = RECIPE_URL + "/" + UUID.randomUUID() + INGREDIENT_URL;
        final CreateIngredientRequest createIngredientRequest = IngredientDataProvider.createIngredientRequest();
        
        // When
        final ResultActions result = mockMvc
            .perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createIngredientRequest))
                .characterEncoding(UTF_CHARSET));
        
        // Then
        result.andExpect(status().isNotFound());
    }
    
    @Test
    void update() throws Exception {
        // Given
        final String url = INGREDIENT_URL + "/" + INGREDIENT_ID;
        final UpdateIngredientRequest updateIngredientRequest = IngredientDataProvider.createUpdateIngredientRequest(INGREDIENT_ID);
        
        // When
        final ResultActions result = mockMvc
            .perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateIngredientRequest))
                .characterEncoding(UTF_CHARSET))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        
        final IngredientResponse ingredientResponse = extractEntity(result.andReturn(), IngredientResponse.class);
        
        // Then
        final Optional<Ingredient> ingredient = ingredientRepository.findById(INGREDIENT_ID);
        assertTrue(ingredient.isPresent());
        IngredientAssert.assertIngredient(ingredient.get(), ingredientResponse);
        IngredientAssert.assertIngredient(updateIngredientRequest, ingredientResponse);
    }
    
    @Test
    void updateFailWhenIngredientIdNotExists() throws Exception {
        // Given
        final String url = INGREDIENT_URL + "/" + UUID.randomUUID();
        final UpdateIngredientRequest updateIngredientRequest = IngredientDataProvider.createUpdateIngredientRequest(INGREDIENT_ID);
        
        // When
        final ResultActions result = mockMvc
            .perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(updateIngredientRequest))
                .characterEncoding(UTF_CHARSET));
        
        // Then
        result.andExpect(status().isNotFound());
    }
    
    @Test
    void delete() throws Exception {
        // Given
        final String url = INGREDIENT_URL + "/" + INGREDIENT_ID_DELETE;
        
        // When
        mockMvc
            .perform(MockMvcRequestBuilders.delete(url))
            .andExpect(status().isNoContent())
            .andReturn();
        
        // Then
        final Optional<Ingredient> ingredient = ingredientRepository.findById(INGREDIENT_ID_DELETE);
        
        assertTrue(ingredient.isEmpty());
    }
    
    @Test
    void deleteFailWhenIngredientIdNotExists() throws Exception {
        // Given
        final String url = INGREDIENT_URL + "/" + UUID.randomUUID();
        
        // When
        final ResultActions result = mockMvc.perform(MockMvcRequestBuilders.delete(url));
        
        // Then
        result.andExpect(status().isNotFound());
    }
}
