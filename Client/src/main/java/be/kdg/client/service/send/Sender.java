package be.kdg.client.service.send;

import be.kdg.client.messaging.RabbitTopology;
import be.kdg.client.service.dto.OrderMessagingDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void sendOrderToBakery(OrderMessagingDto orderMessagingDto) {
        rabbitTemplate.convertAndSend(RabbitTopology.TOPIC_EXCHANGE, "newOrder.bakery", orderMessagingDto);
    }
}
