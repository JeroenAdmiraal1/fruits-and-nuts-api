package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

	WebTestClient webTestClient;

	@Mock
	CategoryRepository categoryRepository;

	@InjectMocks
	CategoryController categoryController;

	@BeforeEach
	void setUp() {
		categoryController = new CategoryController(categoryRepository);
		webTestClient = WebTestClient.bindToController(categoryController).build();
	}

	@Test
	void listOfCategories() {
		BDDMockito.given(categoryRepository.findAll())
				.willReturn(Flux.just(
				Category.builder().description("Fruit").build(),
				Category.builder().description("Nuts").build()));

		webTestClient.get().uri("/categories").exchange().expectBodyList(Category.class).hasSize(2);
	}

	@Test
	void getById() {

		Mono<Category> categoryMono = Mono.just(Category.builder().description("Fruit").build());
		BDDMockito.given(categoryRepository.findById(anyString()))
				.willReturn(categoryMono);

		webTestClient.get().uri("/categories/1").exchange()
				.expectStatus().isOk()
				.expectBody(Category.class);
	}
}