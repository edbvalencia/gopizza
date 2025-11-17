package com.gopizza.apps.production.pizza;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gopizza.production.pizza.application.create.PizzaCreator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pizzas")
@RequiredArgsConstructor
public class PizzaPostController {

    private final PizzaCreator creator;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreatePizzaRequest request) {
        creator.create(
            request.id(),
            request.orderId(),
            request.type(),
            request.size(),
            request.creationTimeMinutes(),
            request.ingredients()
        );
        return ResponseEntity.noContent().build();
    }

}
