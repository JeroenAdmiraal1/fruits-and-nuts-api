package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

	@Mock
	CategoryRepository categoryRepository;

	@InjectMocks
	CategoryServiceImpl categoryService;

	@BeforeEach
	void setUp() {
		categoryService = new CategoryServiceImpl(categoryRepository);
	}

	@Test
	void findAllCategories() {
		BDDMockito.given(categoryRepository.findAll())
				.willReturn(Flux.just(
						Category.builder().description("Fruit").build(),
						Category.builder().description("Nuts").build()));

		Flux<Category> categoryFlux = categoryService.findAllCategories();

		assertEquals(2, categoryFlux.collectList().block().size());
	}

	@Test
	void findById() {
		Mono<Category> categoryMono = Mono.just(Category.builder().description("Fruit").build());

		BDDMockito.given(categoryRepository.findById(anyString()))
				.willReturn(categoryMono);

		Mono<Category> mono = categoryService.findById("1");

		assertEquals("Fruit", mono.block().getDescription());
	}

	@Test
	void saveAllCategories() {
		BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
				.willReturn(Flux.just(Category.builder().build()));

		Mono<Category> categoryToPost = Mono.just(Category.builder().description("Fruit").build());

		Flux<Category> categoryFlux = categoryService.saveAllCategories(categoryToPost);

		assertEquals(1, categoryFlux.collectList().block().size());
	}

	@Test
	void saveCategory() {
		BDDMockito.given(categoryRepository.save(any(Category.class)))
				.willReturn(Mono.just(Category.builder().description("Fruit").build()));

		Category categoryToUpdate = Category.builder().description("Fruit").build();

		Mono<Category> mono = categoryService.saveCategory(categoryToUpdate);

		assertEquals("Fruit", mono.block().getDescription());
	}

	@Test
	void patchCategoryWithChanges() {
		BDDMockito.given(categoryRepository.findById(anyString()))
				.willReturn(Mono.just(Category.builder().description("Nuts").build()));
		BDDMockito.given(categoryRepository.save(any(Category.class)))
				.willReturn(Mono.just(Category.builder().build()));

		Category categoryToUpdate = Category.builder().description("Fruit").build();

		Mono<Category> savedCategory = categoryService.patchCategory("1", categoryToUpdate);

		Mockito.verify(categoryRepository).save(any());
	}

	@Test
	void patchCategoryWithoutChanges() {
		BDDMockito.given(categoryRepository.findById(anyString()))
				.willReturn(Mono.just(Category.builder().description("Nuts").build()));

		Category categoryToUpdate = Category.builder().description("Nuts").build();

		Mono<Category> savedCategory = categoryService.patchCategory("1", categoryToUpdate);

		verify(categoryRepository, never()).save(any());
	}
}