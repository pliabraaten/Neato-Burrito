package com.PL.burritos.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data  // Annotation from lombok that auto generates constructor and basic methods
public class Burrito {

    private Long id;  // Id needed for DB
    private Date createdAt = new Date();  // Track when burrito was created

    @NotNull  // Adds validation to these fields - Validation enforced in controllers
    @Size(min=5, message = "Name must be at least 5 characters long")
    private String name;
    @Size(min=1, message = "You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

}
