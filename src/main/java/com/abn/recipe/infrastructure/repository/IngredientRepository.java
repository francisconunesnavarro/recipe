package com.abn.recipe.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.abn.recipe.infrastructure.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, UUID>, JpaSpecificationExecutor<Ingredient> {

}
