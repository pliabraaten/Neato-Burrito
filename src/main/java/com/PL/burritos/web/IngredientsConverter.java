package com.PL.burritos.web;

import com.PL.burritos.data.IngredientRepository;
import com.PL.burritos.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class IngredientsConverter implements Converter<String, Ingredient> {

    private IngredientRepository ingredientRepository;

    // Pull ingredients from repos to use for String to Type conversion
    @Autowired
    public void IngredientByIdConverter(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // HARD CODED MAPPING
//    private Map<String, Ingredient> ingredientMap = new HashMap<>();
//
//    public IngredientsConverter() {
//        ingredientMap.put("FLTO", new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
//        ingredientMap.put("COTO", new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
//
//        ingredientMap.put("GRBF", new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
//        ingredientMap.put("CARN", new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
//
//        ingredientMap.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
//        ingredientMap.put("LETC", new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
//
//        ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
//        ingredientMap.put("JACK", new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
//
//        ingredientMap.put("SLSA", new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
//        ingredientMap.put("SRCR", new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
//    }

    // Takes ingredient ID string and returns type from map
    @Override
    public Ingredient convert(String id) {
        return ingredientRepository.findById(id).orElse(null);

//        return ingredientMap.get(id);
    }
}
