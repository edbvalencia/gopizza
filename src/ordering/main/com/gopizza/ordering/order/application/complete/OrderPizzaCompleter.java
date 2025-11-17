package com.gopizza.ordering.order.application.complete;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.gopizza.ordering.order.domain.OrderRepository;
import com.gopizza.shared.application.KafkaPublisher;
import com.gopizza.shared.application.RabbitPublisher;
import com.gopizza.shared.domain.OrderCompletedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderPizzaCompleter {

    private final OrderRepository repository;
    private final RabbitPublisher rabbitPublisher;
    private final KafkaPublisher kafkaPublisher;

    public void complete(String orderId, String pizzaId) {
        var order = repository.search(orderId);
        if (order == null) return;

        if (order.isReadyToComplete()) return;
        if (order.isPizzaCompleted(pizzaId)) return;

        var wasCompleted = order.isReadyToComplete();
        order.completePizza(pizzaId);
        repository.save(order);

        if (!wasCompleted && order.isReadyToComplete()) {
            order.complete();
            repository.save(order);

            var event = OrderCompletedEvent.from(order.id(), Instant.now());
            rabbitPublisher.publish(event);
            kafkaPublisher.publish(event);
        }
    }

}
