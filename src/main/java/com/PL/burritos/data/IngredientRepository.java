package com.PL.burritos.data;

import com.PL.burritos.entity.Ingredient;

import java.util.Optional;

// Interface defines operations as method declarations; implementation is
public interface IngredientRepository{

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}
