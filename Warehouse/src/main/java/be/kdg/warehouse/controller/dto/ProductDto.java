package be.kdg.warehouse.controller.dto;

import be.kdg.warehouse.domain.ingredient.Ingredient;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductDto {
    UUID productNumber;
    String productName;
    double price;
    Boolean isDeactivated;
    String description;
    @JsonInclude()
    List<Ingredient> ingredientList;

    public ProductDto() {
    }

    public ProductDto(UUID productNumber, String productName, double price, Boolean isDeactivated, String description) {
        this.productNumber = productNumber;
        this.productName = productName;
        this.price = price;
        this.isDeactivated = isDeactivated;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productNumber=" + productNumber +
                ", productName='" + productName +  '\'' +
", ingredientList=" + ingredientList.toString() +
                '}';
    }
}
