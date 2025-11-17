package com.gopizza.ordering.order.application.complete;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopizza.shared.domain.PizzaCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplateOrderPizzaOnPizzaCreated {

    private final OrderPizzaCompleter completer;
    private final ObjectMapper mapper;

    @RabbitListener(queuesToDeclare = @Queue("pizza.created"))
    public void onRabbit(String json) throws JsonProcessingException {
        var event = mapper.readValue(json, PizzaCreatedEvent.class);
        completer.complete(event.orderId(), event.id());
    }

    // @KafkaListener(topics = "pizza.created")
    public void onKafka(String json) throws JsonProcessingException {
        var event = mapper.readValue(json, PizzaCreatedEvent.class);
        completer.complete(event.orderId(), event.id());
    }

}
