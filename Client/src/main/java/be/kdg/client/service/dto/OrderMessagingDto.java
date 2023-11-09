package be.kdg.client.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter @Setter
public class OrderMessagingDto {
    @NotNull(message = "orderId can't be null")
    private UUID orderId;
    private String specialInstructions;
    private List<OrderLineMessageDto> orderLines;

    public OrderMessagingDto() {
    }

    public OrderMessagingDto(UUID id, String specialInstructions, List<OrderLineMessageDto> orderLines) {
        this.orderId = id;
        this.specialInstructions = specialInstructions;
        this.orderLines = orderLines;
    }
}
