package com.example.varenichnaya.dish;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class DishPageController {

	private final DishService service;

	DishPageController(DishService service) {
		this.service = service;
	}

	@GetMapping("/")
	String indexPage() {
		return "index";
	}

	@GetMapping("/dishes")
	String dishesPage(Model model) {
		model.addAttribute("dishes", service.findAll());
		return "dishes";
	}

	/**
	 * <form action="/dishes" method="post"> <br>
	 * после submit происходит: redirect:/dishes <br>
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/dishes")
	String createDish(DishRequest request) {

		service.create(request);

		return "redirect:/dishes";
	}

	@PostMapping("/dishes/delete/{id}")
	String deleteDish(@PathVariable Long id) {

		service.delete(id);

		return "redirect:/dishes";
	}

	@GetMapping("/dishes/edit/{id}")
	String editDishPage(@PathVariable Long id, Model model) {
		model.addAttribute("dish", service.findById(id));
		return "dish-edit";
	}

	@PostMapping("/dishes/update/{id}")
	String updateDish(@PathVariable Long id, DishRequest request) {

		service.update(id, request);

		return "redirect:/dishes";
	}
}