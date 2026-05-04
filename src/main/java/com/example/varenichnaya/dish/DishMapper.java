package com.example.varenichnaya.dish;

import org.springframework.stereotype.Component;

@Component
public class DishMapper {
    public Dish toEntity(DishRequest request) {
        return new Dish(request.name(), request.description(), request.price(), request.available());
    }

    public DishResponse toResponse(Dish dish) {
        return new DishResponse(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getPrice(),
                dish.isAvailable()
        );
    }

    public void updateEntity(Dish dish, DishRequest request) {
        dish.setName(request.name());
        dish.setDescription(request.description());
        dish.setPrice(request.price());
        dish.setAvailable(request.available());
    }
}
