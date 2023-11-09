package be.kdg.warehouse.controller.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IngredientDto {
    private UUID id;
    private String name;
    private double amount;
    private UUID product_id;

    public IngredientDto() {
    }

    public IngredientDto(String name, double amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "IngredientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", product_id=" + product_id +
                '}';
    }
}
