package be.kdg.bakery.repository;

import be.kdg.bakery.domain.Product;
import be.kdg.bakery.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    Recipe findByProduct(Product product);
    Optional<Recipe> findById(UUID recipeId);
}
