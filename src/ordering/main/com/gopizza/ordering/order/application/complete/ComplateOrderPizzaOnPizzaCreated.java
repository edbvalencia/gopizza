package com.gopizza.ordering.order.application.complete;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gopizza.shared.domain.pizza.PizzaCreatedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplateOrderPizzaOnPizzaCreated {

    private final ObjectMapper mapper;

    public void on(String json) throws JsonProcessingException {
        var event = mapper.readValue(json, PizzaCreatedEvent.class);
    }

}
