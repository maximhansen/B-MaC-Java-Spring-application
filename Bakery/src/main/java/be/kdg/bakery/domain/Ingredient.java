package be.kdg.bakery.domain;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID ingredientNumber;
    private String name;
    private double amount;
    private UUID productNumber;

    public Ingredient() {
    }

    public Ingredient(String name, double amount) {
        this.name = name;
        this.amount = amount;
        this.ingredientNumber = ingredientNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UUID getProduct() {
        return productNumber;
    }

    public void setProduct(UUID product_id) {
        this.productNumber = product_id;
    }

    public UUID getIngredientNumber() {
        return ingredientNumber;
    }

    public void setIngredientNumber(UUID ingredientNumber) {
        this.ingredientNumber = ingredientNumber;
    }
}
