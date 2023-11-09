package be.kdg.warehouse.controller.rest;

import be.kdg.warehouse.controller.dto.IngredientRequestDto;
import be.kdg.warehouse.controller.dto.IngredientStockDto;
import be.kdg.warehouse.domain.ingredient.Ingredient;
import be.kdg.warehouse.service.IngredientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/ingredients")
@Slf4j
public class IngredientRestController {
    private final IngredientService ingredientService;

    public IngredientRestController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<IngredientStockDto>> getAllIngredients() {
        log.info("Get all ingredients");
        try {
            Iterable<Ingredient> ingredient = ingredientService.retrieveAllIngredients();
            List<IngredientStockDto> dtoList = new ArrayList<>();
            for(Ingredient i : ingredient) {
                dtoList.add(new IngredientStockDto(i.getId(), i.getName(), i.getAmount(), i.getUnit(), i.getMinimumStock()));
            }

            if (dtoList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                log.info("Ingredients found");
                return ResponseEntity.ok(dtoList);
            }
        } catch (Exception e) {
            log.error("An error occurred while fetching ingredients: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<IngredientStockDto> getIngredientByName(@RequestParam String name) {
        log.info("Get ingredient by name");
        var ingredient = ingredientService.retrieveIngredient(name);
        log.info("Ingredient found");
        return ingredient.map(value -> new ResponseEntity<>(
                new IngredientStockDto(value.getId(), value.getName(), value.getAmount(), value.getUnit(), value.getMinimumStock()),
                OK
        )).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping("/add")
    public ResponseEntity<IngredientRequestDto> addIngredient(@RequestBody IngredientRequestDto ingredientRequestDto) {
        log.info("Add ingredient");
        try {
            String name = ingredientRequestDto.getName();
            Ingredient ingredient = ingredientService.addIngredient(name);
            log.info("Ingredient added");
            return new ResponseEntity<>(
                    new IngredientRequestDto(ingredient.getId(), ingredient.getName()),
                    HttpStatus.OK
            );
        } catch (Exception e) {
            log.error("Error adding ingredient: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/number")
    public ResponseEntity<UUID> getIngredientNumber(@RequestParam String name) {
        log.info("Get ingredient number");
        var ingredient = ingredientService.retrieveIngredient(name);
        log.info("Ingredient found");
        return ingredient.map(value -> new ResponseEntity<>(
                value.getId(),
                OK
        )).orElseGet(() -> ResponseEntity.noContent().build());
    }
}
