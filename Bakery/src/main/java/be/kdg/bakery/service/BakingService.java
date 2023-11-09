package be.kdg.bakery.service;

import be.kdg.bakery.controller.api.dto.OrderDto;
import be.kdg.bakery.controller.api.dto.OrderLineDto;
import be.kdg.bakery.domain.Ingredient;
import be.kdg.bakery.messaging.RabbitTopology;
import be.kdg.bakery.messaging.dto.OrderIngredientDto;
import be.kdg.bakery.messaging.dto.OrderMessagingDto;
import be.kdg.bakery.service.api.OrderApiService;
import be.kdg.bakery.service.api.dto.IngredientDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class BakingService {
    Logger logger = LoggerFactory.getLogger(BakingService.class);
    private final OrderApiService orderApiService;
    private final IngredientService ingredientService;
    private final RabbitTemplate rabbitTemplate;
    public BakingService(OrderApiService orderApiService, RabbitTemplate rabbitTemplate, IngredientService ingredientService) {
        this.orderApiService = orderApiService;
        this.rabbitTemplate = rabbitTemplate;
        this.ingredientService = ingredientService;
    }

    public void startBaking() {
        List<OrderDto> incomingOrders = orderApiService.getAllConfirmedOrders();
        for (OrderDto order : incomingOrders) {
            getIngredientsFromWarehouse(order);
        }
    }

    private void getIngredientsFromWarehouse(OrderDto order) {
        logger.info("Start baking order: " + order.getOrderId());
        List<OrderLineDto> orderLines = orderApiService.getOrderlinesOfOrder(order.getOrderId());
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        for (OrderLineDto orderLine : orderLines) {
            ingredientDtoList.addAll(getIngredientsOfOrderLine(orderLine));
        }
        OrderIngredientDto orderIngredientDto = new OrderIngredientDto(order.getOrderId(), Date.from(Instant.now()),
                ingredientDtoList);
        sendToWarehouse(orderIngredientDto);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }
    }

    @EventListener
    public void receiveOrder(OrderMessagingDto orderMessagingDto) {
        logger.info("Order received: " + orderMessagingDto.getOrderId());
    }

    @EventListener
    public void orderIngredientsAreDelivered(UUID orderNumber) {
        logger.info("Order ingredients are delivered: " + orderNumber);
        // baking process
        //...
        sendToClient(orderNumber);
    }

    private List<IngredientDto> getIngredientsOfOrderLine(OrderLineDto orderLineDto) {
        List<Ingredient> ingredients = ingredientService.retrieveAllIngredientsByProductNumber(orderLineDto.getProductNumber());
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        for (Ingredient ingredient : ingredients) {
            ingredientDtoList.add(new IngredientDto(ingredient.getIngredientNumber(), ingredient.getAmount()));
        }
        return ingredientDtoList;
    }

    private void sendToWarehouse(OrderIngredientDto orderIngredientDto) {
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "say.#.orderIngredient", orderIngredientDto);
        logger.info("Order sent to warehouse: " + orderIngredientDto.getOrderNumber());
    }

    private void sendToClient(UUID orderId) {
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "say.#.orderConfirmed", orderId);
        logger.info("Order baked sent to client: " + orderId);
    }
}
