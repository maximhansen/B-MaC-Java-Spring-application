package be.kdg.warehouse.controller.dto;

import be.kdg.warehouse.domain.ingredient.Unit;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class IngredientStockDto {

    private UUID number;
    private String name;
    private double amount;
    private Unit unit;
    private double minimumStock;

    public IngredientStockDto() {
    }

     public IngredientStockDto(UUID number, String name, double amount, Unit unit, double minimumStock) {
          this.number = number;
          this.name = name;
          this.amount = amount;
          this.unit = unit;
          this.minimumStock = minimumStock;
     }

    public IngredientStockDto(UUID id, String name) {
        this.number = id;
        this.name = name;
    }
}
