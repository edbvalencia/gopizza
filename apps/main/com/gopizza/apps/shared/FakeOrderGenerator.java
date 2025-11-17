package com.gopizza.apps.shared;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.gopizza.apps.ordering.order.CreateOrderRequest;
import com.gopizza.shared.domain.OrderPizza;
import com.gopizza.shared.domain.PizzaSize;
import com.gopizza.shared.domain.PizzaType;

@Component
public class FakeOrderGenerator {

    private final RestTemplate rest = new RestTemplate();
    private final Random random = new Random();

    @Scheduled(fixedRate = 15000)
    public void generateOrder() {
        CreateOrderRequest request = new CreateOrderRequest(
            NanoIdUtils.randomNanoId(),
            randomPizzas()
        );

        try {
            rest.postForEntity(
                "http://localhost:8082/api/orders",
                request,
                Void.class
            );

            System.out.println("pedido generado: " + request.id());

        } catch (Exception e) {
            System.err.println("error enviando pedido: " + e.getMessage());
        }
    }

    private List<OrderPizza> randomPizzas() {
        int count = random.nextInt(6) + 1;
        List<OrderPizza> pizzas = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            pizzas.add(new OrderPizza(
                UUID.randomUUID().toString(),
                randomType(),
                randomSize(),
                false,
                random.nextInt(11) + 5
            ));
        }

        return pizzas;
    }

    private PizzaType randomType() {
        PizzaType[] values = PizzaType.values();
        return values[random.nextInt(values.length)];
    }

    private PizzaSize randomSize() {
        PizzaSize[] values = PizzaSize.values();
        return values[random.nextInt(values.length)];
    }

}