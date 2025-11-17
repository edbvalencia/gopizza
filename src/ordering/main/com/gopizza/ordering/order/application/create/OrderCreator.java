package com.gopizza.ordering.order.application.create;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gopizza.ordering.order.domain.Order;
import com.gopizza.ordering.order.domain.OrderRepository;
import com.gopizza.shared.application.KafkaPublisher;
import com.gopizza.shared.application.RabbitPublisher;
import com.gopizza.shared.domain.OrderCreatedEvent;
import com.gopizza.shared.domain.OrderPizza;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCreator {

    private final RabbitPublisher publisher;
    private final KafkaPublisher kafkaPublisher;
    private final OrderRepository repository;

    public void create(String id, List<OrderPizza> items) {
        var order = Order.create(id, items);

        repository.save(order);

        publisher.publish(OrderCreatedEvent.from(id, items));
        kafkaPublisher.publish(OrderCreatedEvent.from(id, items));
    }

}
