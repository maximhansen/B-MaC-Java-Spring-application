package be.kdg.client.messaging;

import be.kdg.client.controller.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RestReceiver {

    private final ApplicationEventPublisher publisher;

    public RestReceiver(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_QUEUE_NEW_PRODUCT_CLIENT, messageConverter = "#{jackson2JsonMessageConverter}")
    public void newProduct(ProductDto productDto) {
        log.info("New product received: " + productDto.toString());
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_QUEUE_ACTIVATION_PRODUCT_CLIENT, messageConverter = "#{jackson2JsonMessageConverter}")
    public void activationChanged(String message) {
        log.info("Product deactivated:  " + message);
    }

    @RabbitListener(queues = RabbitTopology.TOPIC_QUEUE_ORDER, messageConverter = "#{jackson2JsonMessageConverter}")
    public void orderReady(UUID orderId) {
        publisher.publishEvent(orderId);
    }
}
