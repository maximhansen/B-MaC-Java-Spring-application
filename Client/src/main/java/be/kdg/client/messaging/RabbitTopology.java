package be.kdg.client.messaging;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopology {

    public static final String TOPIC_EXCHANGE = "exchange";
    public static final String TOPIC_QUEUE_NEW_PRODUCT_CLIENT = "new-product-queue-client";
    public static final String TOPIC_QUEUE_ACTIVATION_PRODUCT_CLIENT = "new-product-queue-client";
    public static final String TOPIC_QUEUE_ORDER = "order-queue";

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue topicQueueProduct(){
        return new Queue(TOPIC_QUEUE_NEW_PRODUCT_CLIENT,false);
    }

    @Bean
    public Queue topicQueueActivationProduct(){
        return new Queue(TOPIC_QUEUE_ACTIVATION_PRODUCT_CLIENT,false);
    }

    @Bean
    public Queue topicQueueOrder(){
        return new Queue(TOPIC_QUEUE_ORDER,false);
    }

    @Bean
    public Binding topicProductBinding(TopicExchange topicExchange, Queue topicQueueProduct ){
        return BindingBuilder.bind(topicQueueProduct).to(topicExchange).with("newProduct.client");
    }

    @Bean
    public Binding topicActivationProductBinding(TopicExchange topicExchange, Queue topicQueueActivationProduct ){
        return BindingBuilder.bind(topicQueueActivationProduct).to(topicExchange).with("activateProduct.client");
    }

    @Bean
    public Binding topicOrderBinding(TopicExchange topicExchange, Queue topicQueueOrder ){
        return BindingBuilder.bind(topicQueueOrder).to(topicExchange).with("say.#.orderConfirmed");
    }

    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
