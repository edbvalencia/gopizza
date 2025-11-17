package com.gopizza.production.pizza.application.create;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopizza.shared.domain.order.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreatePizzaOnOrderCreated {

    private static final int MIN_SECONDS = 1;
    private static final int MAX_SECONDS = 5;

    private final ObjectMapper mapper;

    public void on(String json) throws JsonProcessingException {
        OrderCreatedEvent event = mapper.readValue(json, OrderCreatedEvent.class);
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
