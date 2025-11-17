package com.gopizza.apps.production.pizza;

import java.util.List;

import com.gopizza.shared.domain.PizzaIngredient;
import com.gopizza.shared.domain.PizzaSize;
import com.gopizza.shared.domain.PizzaType;

public record CreatePizzaRequest(
    String id,
    String orderId,
    PizzaType type,
    PizzaSize size,
    int creationTimeMinutes,
    List<PizzaIngredient> ingredients
) {
}
