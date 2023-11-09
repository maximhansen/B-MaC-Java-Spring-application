package be.kdg.bakery.service;

import be.kdg.bakery.domain.Product;
import be.kdg.bakery.domain.Recipe;
import be.kdg.bakery.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RecipeService {
    private final RecipeRepository repository;
    public RecipeService(RecipeRepository repository) {
        this.repository = repository;
    }

    public Recipe retrieveRecipe(Product product) {
        return repository.findByProduct(product);
    }

    public Optional<Recipe> retrieveRecipeById(UUID recipeId) {
        return repository.findById(recipeId);
    }

    public void saveRecipe(Recipe recipe) {
        repository.save(recipe);
    }
}
