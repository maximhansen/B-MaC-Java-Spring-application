package be.kdg.bakery.messaging.dto;
import be.kdg.bakery.service.api.dto.IngredientDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderIngredientDto {
    private UUID orderNumber;
    private Date timestamp;
    private List<IngredientDto> ingredients;

    public OrderIngredientDto() {
    }

    public OrderIngredientDto(UUID orderNumber, Date timestamp, List<IngredientDto> ingredients) {
        this.orderNumber = orderNumber;
        this.timestamp = timestamp;
        this.ingredients = ingredients;
    }

    public UUID getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(UUID orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "OrderIngredientDto{" +
                "OrderNumber=" + orderNumber +
                ", timestamp=" + timestamp +
                ", ingredientNumber=" + ingredients.toString() +
                '}';
    }
}
