package com.abn.recipe.api;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abn.recipe.api.contract.CreateIngredientRequest;
import com.abn.recipe.api.contract.IngredientResponse;
import com.abn.recipe.api.contract.UpdateIngredientRequest;
import com.abn.recipe.domain.service.IngredientService;
import com.abn.recipe.infrastructure.entity.Ingredient;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientApi {
    
    IngredientService ingredientService;
    
    @Transactional
    @GetMapping(value = "/ingredients")
    public List<IngredientResponse> findAll() {
        log.debug("findAll. ");
        return IngredientResponse
            .toResponse(ingredientService
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Ingredient::getName))
                .toList());
    }
    
    @Transactional
    @GetMapping(value = "/recipes/{recipeId}/ingredients")
    public List<IngredientResponse> findAllByRecipeId(
        @NonNull @PathVariable final UUID recipeId
    ) {
        log.debug("findAllByRecipeId. recipeId={}", recipeId);
        return IngredientResponse
            .toResponse(ingredientService
                .findAllByRecipeId(recipeId)
                .stream()
                .sorted(Comparator.comparing(Ingredient::getName))
                .toList());
    }
    
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/recipes/{recipeId}/ingredients", consumes = MediaType.APPLICATION_JSON_VALUE)
    public IngredientResponse create(
        @NonNull @PathVariable final UUID recipeId,
        @NonNull @Valid @RequestBody final CreateIngredientRequest createIngredientRequest
    ) {
        log.debug("create. recipeId={}, createIngredientRequest={}", recipeId, createIngredientRequest);
        return IngredientResponse
            .toResponse(ingredientService.create(recipeId, createIngredientRequest.toEntity()));
    }
    
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/ingredients/{ingredientId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public IngredientResponse update(
        @NonNull @PathVariable final UUID ingredientId,
        @NonNull @RequestBody final UpdateIngredientRequest updateIngredientRequest
    ) {
        log.debug("update. ingredientId={}, updateIngredientRequest={}", ingredientId, updateIngredientRequest);
        return IngredientResponse
            .toResponse(ingredientService.update(updateIngredientRequest.toEntity(ingredientId)));
    }
    
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/ingredients/{ingredientId}")
    public void delete(@NonNull @PathVariable final UUID ingredientId) {
        log.debug("delete. ingredientId={}", ingredientId);
        ingredientService.delete(ingredientId);
    }
}
