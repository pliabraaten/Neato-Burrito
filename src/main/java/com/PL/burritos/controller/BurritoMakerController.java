package com.PL.burritos.controller;

import com.PL.burritos.entity.Burrito;
import com.PL.burritos.entity.BurritoOrder;
import com.PL.burritos.entity.Ingredient;
import com.PL.burritos.entity.Ingredient.Type;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j  // Lombok annotation that auto-generates SLF4J (Simple Logging Facade for Java) - Logger
@Controller
@RequestMapping("/maker")  // Handle HTTP requests from path /design
@SessionAttributes("burritoOrder")  // Used to store data in user session to span multiple requests
public class BurritoMakerController {

    // MODEL ATTRIBUTES (model ferries data between controller and view/html)

    // Adds ingredient list into model to be available in view for user to select
    @ModelAttribute
    public void addIngredientsToModel(Model model) {

        // Builds list of ingredients
        List<Ingredient> ingredients = Arrays.asList(
            new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
            new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
            new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
            new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
            new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
            new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
            new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
            new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
            new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
            new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );

        // Takes full ingredient list and sorts by ingredient type
       Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
                  // Helper function below
        }
    }

    // Instantiates new BurritoOrder object and adds it to the model
    @ModelAttribute(name = "burritoOrder")  // Names this attribute to be referenced later
    public BurritoOrder order() {
        return new BurritoOrder();
    // The BurritoOrder holds space for burrito objects to be added
    }

    // Creates new burrito object
    @ModelAttribute(name = "burrito")
    public Burrito burrito() {
        return new Burrito();
    // Burrito object is placed in model so view can display object
    }


    // ENDPOINTS MAPPINGS

    // Send request to view template to render as HTML
    // Mapping for the order form
    @GetMapping  // GET method for the /design path
    public String showMakerForm() {
        return "maker";  // Renders maker.html
    // The maker.html will have access to all the model attributes
    }

    // Receive data from the order form
    @PostMapping  // POST method to gather data
    public String processBurrito(Burrito newBurrito,
                                 @ModelAttribute BurritoOrder burritoOrder) {  // Bind form input to objects
        burritoOrder.addBurrito(newBurrito);  // Add new burrito to order in model

        System.out.println("New burrito: " + newBurrito.getName());

        log.info("Processing burrito: {}", newBurrito);
        return "redirect:/orders/current";
    }


    // HELPER METHODS

    // Filters ingredients list by type
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }



}
