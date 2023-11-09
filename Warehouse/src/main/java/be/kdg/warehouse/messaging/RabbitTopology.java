package be.kdg.warehouse.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopology {

    public static final String TOPIC_EXCHANGE = "exchange";
    public static final String TOPIC_QUEUE_PRODUCT = "product-queue";
    public static final String TOPIC_QUEUE_ORDER = "order-queue";
    public static final String TOPIC_NOT_READY = "not-ready-queue";

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Queue topicQueueProduct(){
        return new Queue(TOPIC_QUEUE_PRODUCT,false);
    }

    @Bean
    public Queue topicQueueOrder(){
        return new Queue(TOPIC_QUEUE_ORDER,false);
    }

    @Bean
    public Queue topicQueueNotReady(){
        return new Queue(TOPIC_NOT_READY,false);
    }

    @Bean
    public Binding topicNotReadyBinding(TopicExchange topicExchange, Queue topicQueueNotReady ){
        return BindingBuilder.bind(topicQueueNotReady).to(topicExchange).with("say.#.not-ready");
    }

    @Bean
    public Binding topicProductBinding(TopicExchange topicExchange, Queue topicQueueProduct ){
        return BindingBuilder.bind(topicQueueProduct).to(topicExchange).with("say.#.product");
    }

    @Bean public Binding topicOrderBinding(TopicExchange topicExchange, Queue topicQueueOrder ){
        return BindingBuilder.bind(topicQueueOrder).to(topicExchange).with("say.#.orderIngredient");
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}


