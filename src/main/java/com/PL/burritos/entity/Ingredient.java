package com.PL.burritos.entity;

import lombok.Data;

@Data  // Lombok annotation to generate constructor and basic methods
public class Ingredient {

    private final String ID;
    private final String name;
    private final Type type;

    public String getIngredient() {
        return ID;
    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }


    // NO Constructor, NO GETTERS/SETTERS:
    // Lombok library automatically generates them at compile time

}
