package be.kdg.client.controller.dto;

import be.kdg.client.domain.order.OrderState;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class OrderClientDto {
    @NotNull(message = "accountId can't be null")
    private UUID accountId;
    private OrderState orderState;
    private LocalDateTime orderDate;
    private String specialInstructions;
    @NotNull(message = "Order without products is not allowed")
    private List<ProductDto> productDtoList;
}
