package com.PL.burritos.entity;

import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Data  // Annotation from lombok that auto generates constructors and basic methods
public class BurritoOrder {

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    private List<Burrito> burritos = new ArrayList<>();

    public void addBurrito(Burrito newBurrito) {
        burritos.add(newBurrito);
    }



}
