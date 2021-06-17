package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

	private CategoryRepository categoryRepository;

	public CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/categories")
	Flux<Category> listOfCategories() {
		return categoryRepository.findAll();
	}

	@GetMapping("/categories/{id}")
	Mono<Category> getById(@PathVariable String id){
		return categoryRepository.findById(id);
	}
}
