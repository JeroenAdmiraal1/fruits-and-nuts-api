package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/categories")
	Mono<Void> createCategories(@RequestBody Publisher<Category> categoryPublisher) {
		return categoryRepository.saveAll(categoryPublisher).then();
	}

	@PutMapping("/categories/{id}")
	Mono<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
		category.setId(id);
		return categoryRepository.save(category);
	}
}
