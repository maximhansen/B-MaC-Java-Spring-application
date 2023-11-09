package be.kdg.warehouse.service;

import be.kdg.warehouse.domain.ingredient.Ingredient;
import be.kdg.warehouse.domain.stock.StockMovement;
import be.kdg.warehouse.domain.stock.StockMovementType;
import be.kdg.warehouse.repository.IngredientRepository;
import be.kdg.warehouse.repository.StockMovementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientService ingredientService;

    public StockMovementService(StockMovementRepository stockMovementRepository, IngredientRepository ingredientRepository,
                                IngredientService ingredientService, ApplicationEventPublisher publisher) {
        this.stockMovementRepository = stockMovementRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientService = ingredientService;
    }

    private void addStock(UUID ingredientId, double amount, LocalDateTime dateTime, StockMovementType type) {
        log.info("Stock movement for Ingredient ID: {}", ingredientId);
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(ingredientId);

        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            double newAmount = calculateNewAmount(ingredient.getAmount(), amount, type);

            if (type == StockMovementType.OUTGOING && newAmount < 0) {
                log.error("Outgoing stock movement would result in a negative stock level. Movement not allowed.");
            } else {
                ingredient.setAmount(newAmount);
                log.info("Ingredient amount updated");
                ingredientRepository.save(ingredient);
                stockMovementRepository.save(new StockMovement(ingredient, amount, dateTime, type));
                log.info("Stock movement saved");
            }
        } else {
            log.error("Ingredient with ID {} not found. Stock movement not executed.", ingredientId);
        }
    }

    private double calculateNewAmount(double currentAmount, double amount, StockMovementType type) {
        if (StockMovementType.INCOMING == type) {
            return currentAmount + amount;
        } else {
            return currentAmount - amount;
        }
    }

    public double getCurrentStock(UUID id) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findById(id);
        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            return ingredient.getAmount();
        } else {
            log.error("Ingredient not found: " + id);
            return 0;
        }
    }

    public void addStockToIngredient(double amount, String name) {
        Optional<Ingredient> optionalIngredient = ingredientService.retrieveIngredient(name);
        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            addStock(ingredient.getId(), amount, LocalDateTime.now(), StockMovementType.INCOMING);
        } else {
            log.error("Ingredient not found: " + name);
        }
    }
}
