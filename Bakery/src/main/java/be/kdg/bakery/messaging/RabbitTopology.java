package be.kdg.bakery.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitTopology {

    public static final String TOPIC_EXCHANGE = "exchange";
    public static final String TOPIC_QUEUE_NEW_ORDER_BAKERY = "new-order-queue-bakery";
    public static final String TOPIC_DELIVER = "deliver-queue";
    public static final String TOPIC_NOT_READY = "not-ready-queue";

    @Bean
    TopicExchange topicExchange(){
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue topicQueueOrder(){
        return new Queue(TOPIC_QUEUE_NEW_ORDER_BAKERY,false);
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
    public Binding topicOrderBinding(TopicExchange topicExchange, Queue topicQueueOrder ){
        return BindingBuilder.bind(topicQueueOrder).to(topicExchange).with("newOrder.bakery");
    }

    @Bean
    public Queue topicQueueDeliver(){
        return new Queue(TOPIC_DELIVER,false);
    }

    @Bean
    public Binding topicDeliverBinding(TopicExchange topicExchange, Queue topicQueueDeliver ){
        return BindingBuilder.bind(topicQueueDeliver).to(topicExchange).with("deliver-queue");
    }

    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
}
