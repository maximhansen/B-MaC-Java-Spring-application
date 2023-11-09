package be.kdg.bakery.controller.api.dto;

import be.kdg.bakery.domain.Ingredient;
import be.kdg.bakery.domain.Product;
import be.kdg.bakery.domain.Recipe;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ProductDto implements Serializable {
    UUID productNumber;
    String productName;
    double price;
    Boolean isDeactivated;
    String description;
    Recipe recipe;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    List<Ingredient> ingredientList;

    public ProductDto(Collection<Product> products) {
    }

    public ProductDto(UUID productNumber, String productName) {
        this.productNumber = productNumber;
        this.productName = productName;
    }

    public ProductDto(UUID productNumber, String productName, String description) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.description = description;
    }

    public ProductDto(UUID productNumber, String productName, String description, double price, Boolean isDeactivated) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.isDeactivated = isDeactivated;
    }

    public ProductDto(UUID productNumber, String productName, String description, double price) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.description = description;
        this.price = price;
    }

    public ProductDto(UUID productNumber, String productName, List<Ingredient> ingredients) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.ingredientList = ingredients;
        this.price = 0.0;
    }

    public UUID getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(UUID productNumber) {
        this.productNumber = productNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Boolean getDeactivated() {
        return isDeactivated;
    }

    public void setDeactivated(Boolean deactivated) {
        isDeactivated = deactivated;
    }


    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productNumber=" + productNumber +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", isDeactivated=" + isDeactivated +
                ", description='" + description + '\'' +
                ", recipe=" + recipe +
                '}';
    }
}
