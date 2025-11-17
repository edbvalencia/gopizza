package com.gopizza.apps.ordering.order;

import java.util.List;

import com.gopizza.shared.domain.OrderPizza;

public record CreateOrderRequest(
    String id,
    List<OrderPizza> pizzas
) {
}
