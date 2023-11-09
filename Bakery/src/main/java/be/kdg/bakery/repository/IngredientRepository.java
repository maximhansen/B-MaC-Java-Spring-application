package be.kdg.bakery.repository;

import be.kdg.bakery.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {

    List<Ingredient> findAll();

    List<Ingredient> findAllByProductNumber(UUID productNumber);

}
