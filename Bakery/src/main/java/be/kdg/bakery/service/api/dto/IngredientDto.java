package be.kdg.bakery.service.api.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Boolean getDangerZone() {
        return dangerZone;
    }

    public void setDangerZone(Boolean dangerZone) {
        this.dangerZone = dangerZone;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(double minimumStock) {
        this.minimumStock = minimumStock;
    }
}
