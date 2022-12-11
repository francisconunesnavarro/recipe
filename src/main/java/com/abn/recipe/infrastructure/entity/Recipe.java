package com.abn.recipe.infrastructure.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = { "ingredients" }, callSuper = true)
@ToString
public class Recipe extends BaseEntity {
    
    String cookTime;
    
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    @Column(columnDefinition = "ingredient_id")
    List<Ingredient> ingredients;
    
    @Column(nullable = false)
    String instructions;
    
    @Column(nullable = false)
    boolean isActive;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    MealType mealType;
    
    @Column(nullable = false)
    String name;
    
    @Column(nullable = false)
    int numberOfServings;
    
    String prepTime;
    
    public Recipe update(@NonNull final Recipe updateRecipe) {
        this.setCookTime(updateRecipe.getCookTime());
        this.setInstructions(updateRecipe.getInstructions());
        this.setActive(updateRecipe.isActive());
        this.setMealType(updateRecipe.getMealType());
        this.setName(updateRecipe.getName());
        this.setNumberOfServings(updateRecipe.getNumberOfServings());
        this.setPrepTime(updateRecipe.getPrepTime());
        return this;
    }
}
