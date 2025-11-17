package com.gopizza.production.pizza.application.create;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gopizza.production.pizza.domain.Pizza;
import com.gopizza.production.pizza.domain.PizzaRepository;
import com.gopizza.shared.application.KafkaPublisher;
import com.gopizza.shared.application.RabbitPublisher;
import com.gopizza.shared.domain.PizzaCreatedEvent;
import com.gopizza.shared.domain.PizzaIngredient;
import com.gopizza.shared.domain.PizzaSize;
import com.gopizza.shared.domain.PizzaType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PizzaCreator {

    private final PizzaRepository repository;
    private final RabbitPublisher rabbitPublisher;
    private final KafkaPublisher kafkaPublisher;

    public Pizza create(
        String id,
        String orderId,
        PizzaType type,
        PizzaSize size,
        int creationTimeMinutes,
        List<PizzaIngredient> ingredients
    ) {
        var pizza = Pizza.create(
            id,
            orderId,
            type,
            size,
            creationTimeMinutes,
            ingredients
        );

        var savedPizza = repository.save(pizza);

        var event = PizzaCreatedEvent.from(
            savedPizza.id(),
            savedPizza.orderId(),
            savedPizza.type(),
            savedPizza.size(),
            savedPizza.createdAt()
        );

        rabbitPublisher.publish(event);
        kafkaPublisher.publish(event);

        return savedPizza;
    }

}
