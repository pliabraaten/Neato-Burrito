package com.PL.burritos.web.controller;

import com.PL.burritos.data.IngredientRepository;
import com.PL.burritos.entity.Burrito;
import com.PL.burritos.entity.BurritoOrder;
import com.PL.burritos.entity.Ingredient;
import com.PL.burritos.entity.Ingredient.Type;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j  // Lombok annotation that auto-generates SLF4J (Simple Logging Facade for Java) - Logger
@Controller
@RequestMapping("/maker")  // Handle HTTP requests from path /design
@SessionAttributes("burritoOrder")  // Used to store data in user session to span multiple requests
public class BurritoMakerController {

    private final IngredientRepository ingredientRepository;

    // Constructor
    @Autowired
    public BurritoMakerController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // MODEL ATTRIBUTES (model ferries data between controller and view/html)

    // Adds ingredient list into model to be available in view for user to select
    @ModelAttribute
    public void addIngredientsToModel(Model model) {

        // Pulls ingredients from DB and makes list (Iterable allows for enhanced for loop)
        Iterable<Ingredient> ingredients = ingredientRepository.findAll();  // Queries via injected repository methods

//        // Builds list of ingredients - HARD CODED WITHOUT DB
//        List<Ingredient> ingredients = Arrays.asList(
//            new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
//            new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
//            new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
//            new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
//            new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
//            new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
//            new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
//            new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
//            new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
//            new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
//        );

        // Convert Iterable ingredients into a List for the filterByType helper method
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredients.forEach(ingredientList::add);

        // Takes the converted ingredient list and sorts by ingredient type
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                filterByType(ingredientList, type));  // Helper function below
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
          // Add @Valid to check validation set in the Burrito class after it's bound to submitted form data
    public String processBurrito(@Valid Burrito newBurrito, Errors errors,
                                 @ModelAttribute BurritoOrder burritoOrder) {  // Bind form input to objects

        if (errors.hasErrors()) {  // If validation errors, stop method and send back to maker.html
            return "maker";
        }

        burritoOrder.addBurrito(newBurrito);  // Add new burrito to order in model
        log.info("Processing burrito: {}", newBurrito);
        return "redirect:/orders/current";
    }


    // HELPER METHODS

    // Filters ingredients list by type
    private Iterable<Ingredient> filterByType(List<Ingredient> ingredientList, Type type) {
        return ingredientList
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
