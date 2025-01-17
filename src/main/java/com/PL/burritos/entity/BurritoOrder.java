package com.PL.burritos.entity;

import lombok.Data;

import java.awt.*;

@Data  // Annotation from lombok that auto generates constructors and basic methods
public class TacoOrder {

    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccNumber;
    private String ccExpiration;
    private String ccCVV;

    public List<Taco> Tacos;



}
