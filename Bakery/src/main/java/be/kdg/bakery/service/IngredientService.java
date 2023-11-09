package be.kdg.bakery.service;

import be.kdg.bakery.domain.Ingredient;
import be.kdg.bakery.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IngredientService {
    private final IngredientRepository repository;

    public IngredientService(IngredientRepository repository) {
        this.repository = repository;
    }

    public void addIngredient(UUID ingredientNumber, Ingredient ingredient) {
        ingredient.setId(ingredientNumber);
        repository.save(ingredient);
    }

    public void deleteIngredient(UUID ingredientNumber) {
        repository.deleteById(ingredientNumber);
    }

    public Optional<Ingredient> retrieveIngredientById(UUID ingredientNumber) {
        return repository.findById(ingredientNumber);
    }

    public void updateIngredient(Ingredient ingredient) {
        repository.save(ingredient);
    }


    public List<Ingredient> retrieveAllIngredientsByProductNumber(UUID productNumber) {
        return repository.findAllByProductNumber(productNumber);
    }
}
