package be.kdg.warehouse.messaging.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class IngredientDto {
    private UUID id;
    private String name;
    private double amount;
    private Boolean dangerZone;
    private LocalDate expiryDate;
    private double minimumStock;

    public IngredientDto() {
    }

    public IngredientDto(String name, double amount, Boolean dangerZone, LocalDate expiryDate, double minimumStock) {
        this.name = name;
        this.amount = amount;
        this.dangerZone = dangerZone;
        this.expiryDate = expiryDate;
        this.minimumStock = minimumStock;
    }

    public IngredientDto(UUID id, double amount) {
        this.id = id;
        this.amount = amount;
    }
}
