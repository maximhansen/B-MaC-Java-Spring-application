package be.kdg.client.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class ProductDto {
    private UUID productNumber;
    private String productName;
    private String specialRequest;
    private double price;
    private Boolean deactivated;
    private String description;
    private int quantity;
    @JsonInclude()
    List<Ingredient> ingredientList;

    @Override
    public String toString() {
        return "ProductDto{" +
                "productNumber=" + productNumber +
                ", productName='" + productName + '\'' +
                '}';
    }
}
