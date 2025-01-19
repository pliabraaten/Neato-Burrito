package com.PL.burritos.entity;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data  // Annotation from lombok that auto generates constructors and basic methods
public class BurritoOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;  // For db tracking
    private Date placedAt;  // When order was made

    @NotBlank(message = "Delivery name is required")  // Validations on inputs
    private String deliveryName;
    @NotBlank(message = "Street is required")
    private String deliveryStreet;
    @NotBlank(message = "City is required")
    private String deliveryCity;
    @NotBlank(message = "State is required")
    private String deliveryState;
    @NotBlank(message = "Zip is required")
    private String deliveryZip;

    // Validation on CC # to pass Luhn's check
    @CreditCardNumber(message = "Not a valid credit card number")
    private String ccNumber;

    // Validation for two-digit month and year
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
    private String ccExpiration;

    // Validation for 3 digit number only
    @Digits(integer=3, fraction=0, message = "Invalid CVV")
    private String ccCVV;

    private List<Burrito> burritos = new ArrayList<>();

    public void addBurrito(Burrito newBurrito) {
        burritos.add(newBurrito);
    }



}
