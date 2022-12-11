package com.abn.recipe.infrastructure.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ingredient")
@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(exclude = { "recipe" }, callSuper = true)
@ToString
public class Ingredient extends BaseEntity {
    
    @Column(nullable = false)
    boolean isActive;
    
    @Column(nullable = false)
    String name;
    
    @Column(nullable = false)
    double quantity;
    
    @Column(nullable = false)
    String unitMeasurement;
    
    @ToString.Exclude
    @ManyToOne(
        fetch = FetchType.LAZY,
        cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH },
        optional = false
    )
    @JoinColumn(name = "recipe_id", nullable = false)
    Recipe recipe;
    
    public Ingredient addRecipe(@NonNull final Recipe recipe) {
        this.setRecipe(recipe);
        return this;
    }
    
    public Ingredient update(@NonNull final Ingredient updateIngredient) {
        this.setActive(updateIngredient.isActive());
        this.setName(updateIngredient.getName());
        this.setQuantity(updateIngredient.getQuantity());
        this.setUnitMeasurement(updateIngredient.getUnitMeasurement());
        return this;
    }
}
