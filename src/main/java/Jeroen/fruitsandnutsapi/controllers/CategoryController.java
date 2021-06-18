package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.services.CategoryService;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/categories")
	Flux<Category> listOfCategories() {
		return categoryService.findAllCategories();
	}

	@GetMapping("/categories/{id}")
	Mono<Category> getById(@PathVariable String id){
		return categoryService.findById(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/categories")
	Mono<Void> createCategories(@RequestBody Publisher<Category> categoryPublisher) {
		return categoryService.saveAllCategories(categoryPublisher).then();
	}

	@PutMapping("/categories/{id}")
	Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
		category.setId(id);
		return categoryService.saveCategory(category);
	}

	@PatchMapping("/categories/{id}")
	Mono<Category> patchCategory(@PathVariable String id, @RequestBody Category category) {
		return categoryService.patchCategory(id, category);
	}
}
