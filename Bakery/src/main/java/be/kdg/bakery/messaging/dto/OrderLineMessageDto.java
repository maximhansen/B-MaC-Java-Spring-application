package be.kdg.bakery.messaging.dto;


import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderLineMessageDto {
    private int quantity;
    private UUID productNumber;

    public OrderLineMessageDto(int quantity, UUID productNumber) {
        this.quantity = quantity;
        this.productNumber = productNumber;
    }

    public OrderLineMessageDto() {
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductNumber(UUID productNumber) {
        this.productNumber = productNumber;
    }

}
