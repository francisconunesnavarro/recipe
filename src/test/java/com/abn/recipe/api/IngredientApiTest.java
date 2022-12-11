package com.abn.recipe.api;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.abn.recipe.api.contract.CreateIngredientRequest;
import com.abn.recipe.api.contract.IngredientResponse;
import com.abn.recipe.api.contract.UpdateIngredientRequest;
import com.abn.recipe.domain.service.IngredientService;
import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.utils.asserts.IngredientAssert;
import com.abn.recipe.utils.data.IngredientDataProvider;
import com.abn.recipe.utils.data.RecipeDataProvider;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class IngredientApiTest {
    
    @InjectMocks
    IngredientApi ingredientApi;
    
    @Mock
    IngredientService ingredientServiceMock;
    
    AutoCloseable closeable;
    
    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
    
    @Test
    void findAll() {
        // Given
        final Ingredient firstIngredientMock = IngredientDataProvider.createIngredient();
        final Ingredient secondIngredientMock = IngredientDataProvider.createIngredient(false, "a newIngredient", 200.0, "g");
        final List<Ingredient> ingredientsMock = List.of(firstIngredientMock, secondIngredientMock);
        
        // When
        when(ingredientServiceMock.findAll()).thenReturn(ingredientsMock);
        final List<IngredientResponse> ingredientsResponse = ingredientApi.findAll();
        
        // Then
        verify(ingredientServiceMock, times(1)).findAll();
        IngredientAssert.assertIngredient(ingredientsMock.get(0), ingredientsResponse.get(1));
        IngredientAssert.assertIngredient(ingredientsMock.get(1), ingredientsResponse.get(0));
    }
    
    @Test
    void findAllByRecipeId() {
        // Given
        final Ingredient firstIngredientMock = IngredientDataProvider.createIngredient();
        final Ingredient secondIngredientMock = IngredientDataProvider.createIngredient(false, "a newIngredient", 200.0, "g");
        final List<Ingredient> ingredientsMock = List.of(firstIngredientMock, secondIngredientMock);
        
        // When
        when(ingredientServiceMock.findAllByRecipeId(any())).thenReturn(ingredientsMock);
        final List<IngredientResponse> ingredientsResponse = ingredientApi.findAllByRecipeId(RecipeDataProvider.ID);
        
        // Then
        verify(ingredientServiceMock, times(1)).findAllByRecipeId(any());
        IngredientAssert.assertIngredient(ingredientsMock.get(0), ingredientsResponse.get(1));
        IngredientAssert.assertIngredient(ingredientsMock.get(1), ingredientsResponse.get(0));
    }
    
    @Test
    void create() {
        // Given
        final CreateIngredientRequest createIngredientRequest = IngredientDataProvider.createIngredientRequest();
        final Ingredient ingredientMock = IngredientDataProvider.createIngredient();
        
        // When
        when(ingredientServiceMock.create(any(), any())).thenReturn(ingredientMock);
        final IngredientResponse ingredientResponse = ingredientApi.create(RecipeDataProvider.ID, createIngredientRequest);
        
        // Then
        verify(ingredientServiceMock, times(1)).create(any(), any());
        IngredientAssert.assertIngredient(ingredientMock, ingredientResponse);
    }
    
    @Test
    void update() {
        // Given
        final UpdateIngredientRequest updateIngredientRequest = IngredientDataProvider.createUpdateIngredientRequest();
        final Ingredient ingredientMock = IngredientDataProvider.createIngredient();
        
        // When
        when(ingredientServiceMock.update(any())).thenReturn(ingredientMock);
        final IngredientResponse ingredientResponse = ingredientApi.update(IngredientDataProvider.ID, updateIngredientRequest);
        
        // Then
        verify(ingredientServiceMock, times(1)).update(any());
        IngredientAssert.assertIngredient(ingredientMock, ingredientResponse);
    }
    
    @Test
    void delete() {
        // When
        doNothing().when(ingredientServiceMock).delete(any());
        ingredientApi.delete(IngredientDataProvider.ID);
        
        // Then
        verify(ingredientServiceMock, times(1)).delete(any());
    }
}
