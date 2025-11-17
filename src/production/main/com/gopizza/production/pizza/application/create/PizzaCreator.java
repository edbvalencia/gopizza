package com.gopizza.production.pizza.application.create;

import org.springframework.stereotype.Service;

import com.gopizza.production.pizza.domain.Pizza;
import com.gopizza.production.pizza.domain.PizzaRepository;
import com.gopizza.shared.application.KafkaPublisher;
import com.gopizza.shared.application.RabbitPublisher;
import com.gopizza.shared.domain.PizzaCreatedEvent;
import com.gopizza.shared.domain.PizzaSize;
import com.gopizza.shared.domain.PizzaType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PizzaCreator {

    private static final int MIN_SECONDS = 1;
    private static final int MAX_SECONDS = 5;

    private final PizzaRepository repository;
    private final RabbitPublisher rabbitPublisher;
    private final KafkaPublisher kafkaPublisher;

    public void createWithRandomDelay(
        String id,
        String orderId,
        PizzaType type,
        PizzaSize size
    ) {
        int seconds = randomSeconds();
        waitSeconds(seconds);
        create(id, orderId, type, size, seconds);
    }

    public Pizza create(
        String id,
        String orderId,
        PizzaType type,
        PizzaSize size,
        int creationTimeSeconds
    ) {
        var pizza = Pizza.create(
            id,
            orderId,
            type,
            size,
            creationTimeSeconds
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
