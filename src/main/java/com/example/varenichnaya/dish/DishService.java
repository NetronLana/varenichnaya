package com.example.varenichnaya.dish;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishService {
    private final DishRepository repository;
    private final DishMapper mapper;

    public DishService(DishRepository repository, DishMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<DishResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DishResponse findById(Long id) {
        return mapper.toResponse(findEntityById(id));
    }

    @Transactional
    public DishResponse create(DishRequest request) {
        Dish savedDish = repository.save(mapper.toEntity(request));
        return mapper.toResponse(savedDish);
    }

    @Transactional
    public DishResponse update(Long id, DishRequest request) {
        Dish dish = findEntityById(id);
        mapper.updateEntity(dish, request);
        return mapper.toResponse(dish);
    }

    @Transactional
    public void delete(Long id) {
        Dish dish = findEntityById(id);
        repository.delete(dish);
    }

    private Dish findEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new DishNotFoundException(id));
    }
}
