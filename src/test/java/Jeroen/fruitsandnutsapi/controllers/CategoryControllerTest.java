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
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
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

	@Test
	void testCreateCategories() {
		BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
				.willReturn(Flux.just(Category.builder().build()));

		Mono<Category> categoryToPost = Mono.just(Category.builder().description("Fruit").build());

		webTestClient.post()
				.uri("/categories")
				.body(categoryToPost, Category.class).exchange()
				.expectStatus().isCreated();

	}

	@Test
	void testUpdateCategory() {
		BDDMockito.given(categoryRepository.save(any(Category.class)))
				.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> categoryToUpdate = Mono.just(Category.builder().description("Fruit").build());

		webTestClient.put()
				.uri("/categories/1")
				.body(categoryToUpdate, Category.class).exchange()
				.expectStatus().isOk();

	}

	@Test
	void patchCategory() {
		BDDMockito.given(categoryRepository.findById(anyString()))
				.willReturn(Mono.just(Category.builder().description("Nuts").build()));
		BDDMockito.given(categoryRepository.save(any(Category.class)))
				.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> categoryToUpdate = Mono.just(Category.builder().description("Fruit").build());

		webTestClient.patch()
				.uri("/categories/1")
				.body(categoryToUpdate, Category.class).exchange()
				.expectStatus().isOk();
	}
}