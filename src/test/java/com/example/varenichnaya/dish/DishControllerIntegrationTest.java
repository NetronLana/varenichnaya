package com.example.varenichnaya.dish;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DishControllerIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DishRepository repository;

	@BeforeEach
	void cleanDatabase() {
		repository.deleteAll();
	}

	@Test
	void shouldCreateAndFindDish() throws Exception {
		String body = """
				{
				  "name": "Vareniki with cherry",
				  "description": "Sweet vareniki",
				  "price": 8.20,
				  "available": true
				}
				""";

		mockMvc.perform(post("/api/dishes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("Vareniki with cherry"));

		mockMvc.perform(get("/api/dishes"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)));
	}

	@Test
	void shouldReturnNotFoundForMissingDish() throws Exception {
		mockMvc.perform(get("/api/dishes/999"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message").value(
						"Dish with id 999 was not found"));
	}

	@Test
	void shouldUpdateDish() throws Exception {
		Dish dish = repository.save(new Dish(
				"Old dish",
				"Old description",
				BigDecimal.valueOf(5.00),
				true));

		String body = """
				{
				  "name": "Vareniki with mushrooms",
				  "description": "Updated description",
				  "price": 9.00,
				  "available": false
				}
				""";

		mockMvc.perform(put("/api/dishes/" + dish.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Vareniki with mushrooms"))
				.andExpect(jsonPath("$.available").value(false));
	}

	@Test
	void shouldDeleteDish() throws Exception {
		Dish dish = repository.save(new Dish(
				"Temporary dish",
				"Will be deleted",
				BigDecimal.valueOf(4.50),
				true));

		mockMvc.perform(delete("/api/dishes/" + dish.getId()))
				.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/dishes/" + dish.getId()))
				.andExpect(status().isNotFound());
	}

	@Test
	void shouldValidateRequest() throws Exception {
		String body = """
				{
				  "name": "",
				  "description": "Bad request",
				  "price": 0,
				  "available": true
				}
				""";

		mockMvc.perform(post("/api/dishes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isBadRequest());
	}
}
