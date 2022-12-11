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

import com.abn.recipe.api.contract.CreateRecipeRequest;
import com.abn.recipe.api.contract.RecipeQueryRequest;
import com.abn.recipe.api.contract.RecipeResponse;
import com.abn.recipe.api.contract.UpdateRecipeRequest;
import com.abn.recipe.domain.service.RecipeService;
import com.abn.recipe.infrastructure.entity.Recipe;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/recipes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RecipeApi {
    
    RecipeService recipeService;
    
    @Transactional
    @GetMapping
    public List<RecipeResponse> findAll(@NonNull @Valid final RecipeQueryRequest recipeQueryRequest) {
        log.debug("findAll. recipeQueryRequest={}", recipeQueryRequest);
        return RecipeResponse
            .toResponse(recipeService
                .findAll(recipeQueryRequest.toModel())
                .stream()
                .sorted(Comparator.comparing(Recipe::getName))
                .toList());
    }
    
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public RecipeResponse create(@NonNull @Valid @RequestBody final CreateRecipeRequest createRecipeRequest) {
        log.debug("create. createRecipeRequest={}", createRecipeRequest);
        return RecipeResponse
            .toResponse(recipeService.create(createRecipeRequest.toEntity()));
    }
    
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{recipeId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RecipeResponse update(
        @NonNull @PathVariable final UUID recipeId,
        @NonNull @Valid @RequestBody final UpdateRecipeRequest updateRecipeRequest
    ) {
        log.debug("update. recipeId={}, updateRecipeRequest={}", recipeId, updateRecipeRequest);
        return RecipeResponse
            .toResponse(recipeService.update(updateRecipeRequest.toEntity(recipeId)));
    }
    
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{recipeId}")
    public void delete(@NonNull @PathVariable final UUID recipeId) {
        log.debug("update. recipeId={}", recipeId);
        recipeService.delete(recipeId);
    }
}
