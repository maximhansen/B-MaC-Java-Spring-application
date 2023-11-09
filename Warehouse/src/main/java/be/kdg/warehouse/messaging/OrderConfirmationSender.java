package be.kdg.warehouse.messaging;

import be.kdg.warehouse.messaging.dto.IngredientDto;
import be.kdg.warehouse.messaging.dto.OrderIngredientDto;
import be.kdg.warehouse.service.StockMovementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class OrderConfirmationSender {

    private final RabbitTemplate rabbitTemplate;
    private final StockMovementService stockMovementService;

    public OrderConfirmationSender(StockMovementService stockMovementService, RabbitTemplate rabbitTemplate) {
        this.stockMovementService = stockMovementService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @EventListener
    public void checkIfOrderCanBeMade(OrderIngredientDto order) {
        boolean canBeMade = true;
        for (IngredientDto ingredientDto : order.getIngredients()) {
            if (checkStock(ingredientDto.getId()) < ingredientDto.getAmount()) {
                canBeMade = false;
                log.warn("Order has no stock for ingredient: " + ingredientDto.getId() + " Current stock: " + checkStock(ingredientDto.getId()) + " Needed: " + ingredientDto.getAmount());
                sendNoStock(ingredientDto);
            }
        }
        if (canBeMade) {
            sendOrderConfirmation(order.getOrderNumber());
        }
    }

    private double checkStock(UUID ingredientNumber) {
        try {
            return stockMovementService.getCurrentStock(ingredientNumber);
        } catch (Exception e) {
            log.error(e.getMessage() + "Order has no stock for ingredient: " + ingredientNumber);
        }
        return 0;
    }

    private void sendOrderConfirmation(UUID orderNumber) {
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "deliver-queue", orderNumber);
    }

    private void sendNoStock(IngredientDto ingredientDto) {
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "not-ready-queue", ingredientDto);
    }
}
