package com.abn.recipe.domain.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abn.recipe.domain.exception.NotFoundException;
import com.abn.recipe.domain.model.RecipeQuery;
import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.infrastructure.repository.RecipeRepository;
import com.abn.recipe.infrastructure.specification.RecipeSpecification;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecipeService {
    
    RecipeRepository recipeRepository;
    
    @Transactional
    public List<Recipe> findAll(@NonNull final RecipeQuery recipeQuery) {
        log.debug("findAll. recipeQuery={}", recipeQuery);
        return recipeRepository
            .findAll(new RecipeSpecification(recipeQuery))
            .stream()
            .sorted(Comparator.comparing(Recipe::getName))
            .toList();
    }
    
    @Transactional
    public Recipe findById(@NonNull final UUID id) {
        log.debug("findById. id={}", id);
        return recipeRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(format("Recipe %s not found", id)));
    }
    
    @Transactional
    public Recipe create(@NonNull final Recipe recipe) {
        log.debug("create. recipe={}", recipe);
        return recipeRepository.save(recipe);
    }
    
    @Transactional
    public Recipe update(@NonNull final Recipe recipeToUpdate) {
        log.debug("update. recipeToUpdate={}", recipeToUpdate);
        final Recipe recipe = findById(recipeToUpdate.getId())
            .update(recipeToUpdate);
        updateIngredients(recipe, recipeToUpdate);
        return recipeRepository.save(recipe);
    }
    
    @Transactional
    public void delete(@NonNull final UUID recipeId) {
        log.debug("delete. recipeId={}", recipeId);
        findById(recipeId);
        recipeRepository.deleteById(recipeId);
    }
    
    protected void updateIngredients(
        @NonNull final Recipe recipe,
        @NonNull final Recipe recipeToUpdate
    ) {
        final List<Ingredient> ingredients = recipe
            .getIngredients()
            .stream()
            .filter(ingredient -> recipeToUpdate
                .getIngredients()
                .stream()
                .anyMatch(ingredientToUpdate -> ingredientToUpdate.getId().equals(ingredient.getId())))
            .toList();
        
        recipe.setIngredients(new ArrayList<>(updateIngredient(ingredients, recipeToUpdate.getIngredients())));
        
    }
    
    private List<Ingredient> updateIngredient(
        @NonNull final List<Ingredient> ingredients,
        @NonNull final List<Ingredient> ingredientsToUpdate
    ) {
        return ingredients
            .stream()
            .map(ingredient -> ingredient.update(findIngredientToUpdate(ingredient, ingredientsToUpdate)))
            .toList();
    }
    
    private Ingredient findIngredientToUpdate(
        @NonNull final Ingredient ingredient,
        @NonNull final List<Ingredient> ingredientsToUpdate
    ) {
        return ingredientsToUpdate
            .stream()
            .filter(ingredientToUpdate -> ingredientToUpdate.getId().equals(ingredient.getId()))
            .findFirst()
            .orElseThrow(() -> new NotFoundException(format("Ingredient '%s' not found", ingredient.getName())));
    }
}
