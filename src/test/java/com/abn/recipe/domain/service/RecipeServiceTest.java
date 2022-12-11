package com.abn.recipe.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import com.abn.recipe.domain.exception.NotFoundException;
import com.abn.recipe.domain.model.RecipeQuery;
import com.abn.recipe.infrastructure.entity.MealType;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.infrastructure.repository.RecipeRepository;
import com.abn.recipe.infrastructure.specification.RecipeSpecification;
import com.abn.recipe.utils.data.RecipeDataProvider;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecipeServiceTest {
    
    @Spy
    @InjectMocks
    RecipeService recipeService;
    
    @Mock
    RecipeRepository recipeRepositoryMock;
    
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
        final RecipeQuery recipeQueryMock = RecipeDataProvider.createRecipeQuery();
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
        when(recipeRepositoryMock.findAll(any(RecipeSpecification.class))).thenReturn(recipesMock);
        final List<Recipe> recipes = recipeService.findAll(recipeQueryMock);
        
        // Then
        verify(recipeRepositoryMock, times(1)).findAll(any(RecipeSpecification.class));
        assertEquals(recipesMock.size(), recipes.size());
        assertEquals(recipesMock.get(0), recipes.get(1));
        assertEquals(recipesMock.get(1), recipes.get(0));
    }
    
    @Test
    void findById() {
        // Given
        final UUID recipeId = RecipeDataProvider.ID;
        final Recipe firstRecipeMock = RecipeDataProvider.createRecipe();
        
        // When
        when(recipeRepositoryMock.findById(any())).thenReturn(Optional.of(firstRecipeMock));
        final Recipe recipe = recipeService.findById(recipeId);
        
        // Then
        verify(recipeRepositoryMock, times(1)).findById(any());
        assertEquals(firstRecipeMock, recipe);
    }
    
    @Test
    void findByIdFailWhenIdNotExists() {
        // When
        when(recipeRepositoryMock.findById(any())).thenReturn(Optional.empty());
        
        // Then
        assertThrows(NotFoundException.class, () -> recipeService.findById(UUID.randomUUID()));
    }
    
    @Test
    void create() {
        // Given
        final Recipe recipeMock = RecipeDataProvider.createRecipe();
        
        // When
        when(recipeRepositoryMock.save(any())).thenReturn(recipeMock);
        final Recipe recipe = recipeService.create(recipeMock);
        
        // Then
        verify(recipeRepositoryMock, times(1)).save(any());
        assertEquals(recipeMock, recipe);
    }
    
    @Test
    void update() {
        // Given
        final Recipe recipeMock = RecipeDataProvider.createRecipe();
        
        // When
        doReturn(recipeMock).when(recipeService).findById(any());
        doNothing().when(recipeService).updateIngredients(any(), any());
        when(recipeRepositoryMock.save(any())).thenReturn(recipeMock);
        final Recipe recipe = recipeService.update(recipeMock);
        
        // Then
        verify(recipeService, times(1)).findById(any());
        verify(recipeRepositoryMock, times(1)).save(any());
        assertEquals(recipeMock, recipe);
    }
    
    @Test
    void delete() {
        // Given
        final Recipe recipeMock = RecipeDataProvider.createRecipe();
        
        // When
        doReturn(recipeMock).when(recipeService).findById(any());
        doNothing().when(recipeRepositoryMock).deleteById(any());
        recipeService.delete(RecipeDataProvider.ID);
        
        // Then
        verify(recipeRepositoryMock, times(1)).deleteById(any());
    }
}
