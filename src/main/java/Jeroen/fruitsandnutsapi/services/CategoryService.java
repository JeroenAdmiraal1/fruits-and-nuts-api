package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.domain.Category;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

	Flux<Category> findAllCategories();

	Mono<Category> findById(String id);

	Flux<Category> saveAllCategories(Publisher<Category> publisher);

	Mono<Category> saveCategory(Category category);

	Mono<Category> patchCategory(String id, Category category);
}
