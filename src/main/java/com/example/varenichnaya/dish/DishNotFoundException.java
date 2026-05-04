package com.example.varenichnaya.dish;

public class DishNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DishNotFoundException(Long id) {
        super("Dish with id " + id + " was not found");
    }
}
