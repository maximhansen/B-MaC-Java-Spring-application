package be.kdg.bakery.messaging;

import be.kdg.bakery.messaging.dto.OrderMessagingDto;
import be.kdg.bakery.messaging.dto.ResponseDto;
import be.kdg.bakery.service.api.dto.IngredientDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Receiver {

    Logger logger = LoggerFactory.getLogger(Receiver.class);
    private final ApplicationEventPublisher publisher;

    public Receiver(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_QUEUE_NEW_ORDER_BAKERY, messageConverter = "#{jackson2JsonMessageConverter}")
    public void newOrder(OrderMessagingDto orderMessagingDto) {
        publisher.publishEvent(orderMessagingDto);
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_DELIVER, messageConverter = "#{jackson2JsonMessageConverter}")
    public void orderConfirmation(UUID orderNumber) {
        publisher.publishEvent(orderNumber);
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_NOT_READY, messageConverter = "#{jackson2JsonMessageConverter}")
    public void orderHaveNoStock(IngredientDto ingredientDto) {
        logger.warn("Order have no stock for ingredient: " + ingredientDto.getId());
    }
}
