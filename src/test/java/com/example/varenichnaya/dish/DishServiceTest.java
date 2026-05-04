package com.example.varenichnaya.dish;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class DishServiceTest {
    private final DishRepository repository = mock(DishRepository.class);
    private final DishMapper mapper = new DishMapper();
    private final DishService service = new DishService(repository, mapper);

    @Test
    void createShouldSaveDish() {
        DishRequest request = new DishRequest(
                "Vareniki with potato",
                "Classic vareniki",
                BigDecimal.valueOf(7.50),
                true
        );

        Dish savedDish = new Dish(
                request.name(),
                request.description(),
                request.price(),
                request.available()
        );

        when(repository.save(any(Dish.class))).thenReturn(savedDish);

        DishResponse response = service.create(request);

        assertThat(response.name()).isEqualTo("Vareniki with potato");
        assertThat(response.price()).isEqualByComparingTo("7.50");
        verify(repository).save(any(Dish.class));
    }

    @Test
    void findByIdShouldThrowWhenDishDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(DishNotFoundException.class)
                .hasMessageContaining("99");
    }
}
