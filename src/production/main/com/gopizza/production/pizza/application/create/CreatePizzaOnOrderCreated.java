package com.gopizza.production.pizza.application.create;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopizza.shared.domain.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePizzaOnOrderCreated {

    private final PizzaCreator creator;
    private final ObjectMapper mapper;

    @RabbitListener(queuesToDeclare = @Queue("order.created"))
    public void onRabbit(String json) throws JsonProcessingException {
        OrderCreatedEvent event = mapper.readValue(json, OrderCreatedEvent.class);

        event.pizzas().forEach(pizza -> {
            creator.createWithRandomDelay(
                pizza.id(),
                event.id(),
                pizza.type(),
                pizza.size()
            );

        });
    }

    // @KafkaListener(topics = "order.created")
    public void onKafka(String json) throws JsonProcessingException {
        OrderCreatedEvent event = mapper.readValue(json, OrderCreatedEvent.class);

        event.pizzas().forEach(pizza -> {
            creator.createWithRandomDelay(
                pizza.id(),
                event.id(),
                pizza.type(),
                pizza.size()
            );

        });
    }

}
