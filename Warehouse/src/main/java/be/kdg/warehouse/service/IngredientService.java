package be.kdg.warehouse.service;

import be.kdg.warehouse.domain.ingredient.Ingredient;
import be.kdg.warehouse.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Optional<Ingredient> retrieveIngredient(String name) {
        return ingredientRepository.findByName(name);
    }

    public Collection<Ingredient> retrieveAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient addIngredient(String name) {
        return ingredientRepository.save(new Ingredient(name));
    }
}
