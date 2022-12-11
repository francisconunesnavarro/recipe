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
import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.infrastructure.repository.IngredientRepository;
import com.abn.recipe.infrastructure.specification.IngredientSpecification;
import com.abn.recipe.utils.data.IngredientDataProvider;
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
public class IngredientServiceTest {
    
    @Spy
    @InjectMocks
    IngredientService ingredientService;
    
    @Mock
    IngredientRepository ingredientRepositoryMock;
    
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
        final Ingredient firstIngredientMock = IngredientDataProvider.createIngredient();
        final Ingredient secondIngredientMock = IngredientDataProvider.createIngredient(false, "newIngredient", 200.0, "g");
        final List<Ingredient> ingredientsMock = List.of(firstIngredientMock, secondIngredientMock);
        
        // When
        when(ingredientRepositoryMock.findAll()).thenReturn(ingredientsMock);
        final List<Ingredient> ingredients = ingredientService.findAll();
        
        // Then
        verify(ingredientRepositoryMock, times(1)).findAll();
        assertEquals(ingredientsMock, ingredients);
    }
    
    @Test
    void findAllByRecipeId() {
        // Given
        final UUID recipeId = RecipeDataProvider.ID;
        final Ingredient firstIngredientMock = IngredientDataProvider.createIngredient();
        final Ingredient secondIngredientMock = IngredientDataProvider.createIngredient(
            false,
            "newIngredient",
            200.0,
            "g"
        );
        final List<Ingredient> ingredientsMock = List.of(firstIngredientMock, secondIngredientMock);
        
        // When
        when(ingredientRepositoryMock.findAll(any(IngredientSpecification.class))).thenReturn(ingredientsMock);
        final List<Ingredient> ingredients = ingredientService.findAllByRecipeId(recipeId);
        
        // Then
        verify(ingredientRepositoryMock, times(1)).findAll(any(IngredientSpecification.class));
        assertEquals(ingredientsMock, ingredients);
    }
    
    @Test
    void findById() {
        // Given
        final UUID ingredientId = IngredientDataProvider.ID;
        final Ingredient ingredientMock = IngredientDataProvider.createIngredient();
        
        // When
        when(ingredientRepositoryMock.findById(any())).thenReturn(Optional.of(ingredientMock));
        final Ingredient ingredient = ingredientService.findById(ingredientId);
        
        // Then
        verify(ingredientRepositoryMock, times(1)).findById(any());
        assertEquals(ingredientMock, ingredient);
    }
    
    @Test
    void findByIdFailWhenIdNotExists() {
        // When
        when(ingredientRepositoryMock.findById(any())).thenReturn(Optional.empty());
        
        // Then
        assertThrows(NotFoundException.class, () -> ingredientService.findById(UUID.randomUUID()));
    }
    
    @Test
    void create() {
        // Given
        final UUID recipeId = RecipeDataProvider.ID;
        final Recipe recipeMock = RecipeDataProvider.createRecipe();
        final Ingredient ingredientMock = IngredientDataProvider.createIngredient();
        
        // When
        when(recipeServiceMock.findById(any())).thenReturn(recipeMock);
        when(ingredientRepositoryMock.save(any())).thenReturn(ingredientMock);
        final Ingredient ingredient = ingredientService.create(recipeId, ingredientMock);
        
        // Then
        verify(recipeServiceMock, times(1)).findById(any());
        verify(ingredientRepositoryMock, times(1)).save(any());
        assertEquals(ingredientMock, ingredient);
    }
    
    @Test
    void update() {
        // Given
        final Ingredient ingredientMock = IngredientDataProvider.createIngredient();
        
        // When
        doReturn(ingredientMock).when(ingredientService).findById(any());
        when(ingredientRepositoryMock.saveAndFlush(any())).thenReturn(ingredientMock);
        final Ingredient ingredient = ingredientService.update(ingredientMock);
        
        // Then
        verify(ingredientService, times(1)).findById(any());
        verify(ingredientRepositoryMock, times(1)).saveAndFlush(any());
        assertEquals(ingredientMock, ingredient);
    }
    
    @Test
    void delete() {
        // Given
        final Ingredient ingredientMock = IngredientDataProvider.createIngredient();
        
        // When
        doReturn(ingredientMock).when(ingredientService).findById(any());
        doNothing().when(ingredientRepositoryMock).deleteById(any());
        ingredientService.delete(IngredientDataProvider.ID);
        
        // Then
        verify(ingredientService, times(1)).findById(any());
        verify(ingredientRepositoryMock, times(1)).deleteById(any());
    }
}
