package com.abn.recipe.api;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.abn.recipe.api.contract.CreateRecipeRequest;
import com.abn.recipe.api.contract.RecipeQueryRequest;
import com.abn.recipe.api.contract.RecipeResponse;
import com.abn.recipe.api.contract.UpdateRecipeRequest;
import com.abn.recipe.domain.service.RecipeService;
import com.abn.recipe.infrastructure.entity.MealType;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.utils.asserts.RecipeAssert;
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
public class RecipeApiTest {
    
    @InjectMocks
    RecipeApi recipeApi;
    
    @Mock
    RecipeService recipeServiceMock;
    
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
        final RecipeQueryRequest recipeQueryRequest = RecipeDataProvider.createRecipeQueryRequest();
        final Recipe firstRecipeMock = RecipeDataProvider.createRecipe();
        final Recipe secondRecipeMock = RecipeDataProvider.createRecipe(
            "10 min",
            "some instructions",
            false, MealType.VEGAN,
            "newRecipe",
            20,
            "40 min"
        );
        final List<Recipe> recipesMock = List.of(firstRecipeMock, secondRecipeMock);
        
        // When
        when(recipeServiceMock.findAll(any())).thenReturn(recipesMock);
        final List<RecipeResponse> recipesResponse = recipeApi.findAll(recipeQueryRequest);
        
        // Then
        verify(recipeServiceMock, times(1)).findAll(any());
        RecipeAssert.assertRecipe(recipesMock.get(0), recipesResponse.get(1));
        RecipeAssert.assertRecipe(recipesMock.get(1), recipesResponse.get(0));
    }
    
    @Test
    void create() {
        // Given
        final CreateRecipeRequest createRecipeRequest = RecipeDataProvider.createRecipeRequest();
        final Recipe recipeMock = RecipeDataProvider.createRecipe();
        
        // When
        when(recipeServiceMock.create(any())).thenReturn(recipeMock);
        final RecipeResponse recipeResponse = recipeApi.create(createRecipeRequest);
        
        // Then
        verify(recipeServiceMock, times(1)).create(any());
        RecipeAssert.assertRecipe(recipeMock, recipeResponse);
    }
    
    @Test
    void update() {
        // Given
        final UpdateRecipeRequest updateIngredientRequest = RecipeDataProvider.createUpdateRecipeRequest();
        final Recipe recipeMock = RecipeDataProvider.createRecipe();
        
        // When
        when(recipeServiceMock.update(any())).thenReturn(recipeMock);
        final RecipeResponse recipeResponse = recipeApi.update(RecipeDataProvider.ID, updateIngredientRequest);
        
        // Then
        verify(recipeServiceMock, times(1)).update(any());
        RecipeAssert.assertRecipe(recipeMock, recipeResponse);
    }
    
    @Test
    void delete() {
        // When
        doNothing().when(recipeServiceMock).delete(any());
        recipeApi.delete(RecipeDataProvider.ID);
        
        // Then
        verify(recipeServiceMock, times(1)).delete(any());
    }
}
