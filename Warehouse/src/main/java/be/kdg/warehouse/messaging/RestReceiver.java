package be.kdg.warehouse.messaging;

import be.kdg.warehouse.controller.dto.ProductDto;
import be.kdg.warehouse.messaging.dto.OrderIngredientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RestReceiver {
    private final ApplicationEventPublisher publisher;

    public RestReceiver(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_QUEUE_PRODUCT, messageConverter = "#{jackson2JsonMessageConverter}")
    public void newProduct(ProductDto message) {
        System.out.println(message.toString());
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_QUEUE_ORDER, messageConverter = "#{jackson2JsonMessageConverter}")
    public void newOrder(OrderIngredientDto orderIngredientDto) {
        log.info("Order received from bakery: " + orderIngredientDto.getOrderNumber());
        publisher.publishEvent(orderIngredientDto);
    }

}
