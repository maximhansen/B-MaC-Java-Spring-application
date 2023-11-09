package be.kdg.bakery.messaging.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
public class OrderMessagingDto {
    @NotNull(message = "orderId can't be null")
    private UUID orderId;
    private String specialInstructions;
    private List<OrderLineMessageDto> orderLines;

    public OrderMessagingDto() {
    }

    public OrderMessagingDto(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        return "OrderMessagingDto{" +
                "orderId=" + orderId +
                ", specialInstructions='" + specialInstructions + '\'' +
                ", orderLines=" + orderLines +
                '}';
    }
}
