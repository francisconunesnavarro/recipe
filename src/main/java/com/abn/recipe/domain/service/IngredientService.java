package com.abn.recipe.domain.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abn.recipe.domain.exception.NotFoundException;
import com.abn.recipe.domain.model.IngredientQuery;
import com.abn.recipe.infrastructure.entity.Ingredient;
import com.abn.recipe.infrastructure.entity.Recipe;
import com.abn.recipe.infrastructure.repository.IngredientRepository;
import com.abn.recipe.infrastructure.specification.IngredientSpecification;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IngredientService {
    
    IngredientRepository ingredientRepository;
    RecipeService recipeService;
    
    public IngredientService(
        final IngredientRepository ingredientRepository,
        @Lazy final RecipeService recipeService
    ) {
        this.ingredientRepository = ingredientRepository;
        this.recipeService = recipeService;
    }
    
    @Transactional
    public List<Ingredient> findAll() {
        log.debug("findAll. ");
        return ingredientRepository.findAll();
    }
    
    @Transactional
    public List<Ingredient> findAllByRecipeId(@NonNull final UUID recipeId) {
        log.debug("findAllByRecipeId. recipeId={}", recipeId);
        return ingredientRepository.findAll(createIngredientSpecification(recipeId));
    }
    
    @Transactional
    public Ingredient findById(@NonNull final UUID id) {
        log.debug("findById. id={}", id);
        return ingredientRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(format("Ingredient %s not found", id)));
    }
    
    @Transactional
    public Ingredient create(
        @NonNull final UUID recipeId,
        @NonNull final Ingredient ingredient
    ) {
        log.debug("create. recipeId={}, ingredient={}", recipeId, ingredient);
        final Recipe recipe = recipeService.findById(recipeId);
        return ingredientRepository.save(ingredient.addRecipe(recipe));
    }
    
    @Transactional
    public Ingredient update(@NonNull final Ingredient updateIngredient) {
        log.debug("update. updateIngredient={}", updateIngredient);
        final Ingredient ingredient = findById(updateIngredient.getId());
        return ingredientRepository.saveAndFlush(ingredient.update(updateIngredient));
    }
    
    @Transactional
    public void delete(@NonNull final UUID ingredientId) {
        log.debug("delete. ingredientId={}", ingredientId);
        findById(ingredientId);
        ingredientRepository.deleteById(ingredientId);
    }
    
    private IngredientSpecification createIngredientSpecification(@NonNull final UUID recipeId) {
        final IngredientQuery ingredientQuery = new IngredientQuery(Optional.of(recipeId));
        return new IngredientSpecification(ingredientQuery);
    }
}
