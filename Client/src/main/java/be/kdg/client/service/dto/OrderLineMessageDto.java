package be.kdg.client.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class OrderLineMessageDto {
    private int quantity;
    private UUID productNumber;

    public OrderLineMessageDto(int quantity, UUID productNumber) {
        this.quantity = quantity;
        this.productNumber = productNumber;
    }
}
