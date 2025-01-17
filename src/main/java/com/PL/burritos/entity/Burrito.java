package com.PL.burritos.entity;

import lombok.Data;

import java.util.List;

@Data  // Annotation from lombok that auto generates constructor and basic methods
public class Taco {

    private String name;
    private List<Ingredient> Ingredients;

}
