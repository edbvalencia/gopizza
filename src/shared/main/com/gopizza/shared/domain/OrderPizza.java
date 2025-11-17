package com.gopizza.shared.domain;

public record OrderPizza(
    String id,
    PizzaType type,
    PizzaSize size,
    boolean completed,
    double price
) {
    public OrderPizza complete() {
        return new OrderPizza(id, type, size, true, price);
    }
}
