package com.example.varenichnaya.dish;

import java.math.BigDecimal;

public record DishResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        boolean available
) {
}
