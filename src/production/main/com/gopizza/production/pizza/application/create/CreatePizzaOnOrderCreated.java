package com.gopizza.production.pizza.application.create;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopizza.shared.domain.OrderCreatedEvent;
import com.gopizza.shared.domain.PizzaIngredientsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePizzaOnOrderCreated {

    private static final int MIN_SECONDS = 1;
    private static final int MAX_SECONDS = 5;

    private final PizzaCreator creator;
    private final ObjectMapper mapper;

    @RabbitListener(queuesToDeclare = @Queue("order.created"))
    public void onRabbit(String json) throws JsonProcessingException {
        OrderCreatedEvent event = mapper.readValue(json, OrderCreatedEvent.class);

        event.pizzas().forEach(pizza -> {
            var seconds = randomSeconds();

            waitSeconds(seconds);

            creator.create(
                pizza.id(),
                event.id(),
                pizza.type(),
                pizza.size(),
                seconds,
                PizzaIngredientsMapper.generate(pizza.type())
            );
        });
    }

    // @KafkaListener(topics = "order.created")
    public void onKafka(String json) throws JsonProcessingException {
        OrderCreatedEvent event = mapper.readValue(json, OrderCreatedEvent.class);

        event.pizzas().forEach(pizza -> {
            var seconds = randomSeconds();

            waitSeconds(seconds);

            creator.create(
                pizza.id(),
                event.id(),
                pizza.type(),
                pizza.size(),
                seconds,
                PizzaIngredientsMapper.generate(pizza.type())
            );
        });
    }

    private int randomSeconds() {
        return MIN_SECONDS + (int) (Math.random() * (MAX_SECONDS - MIN_SECONDS + 1));
    }

    private void waitSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException ignored) {
        }
    }

}
