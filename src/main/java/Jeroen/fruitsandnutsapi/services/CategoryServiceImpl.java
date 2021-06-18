package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Flux<Category> findAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Mono<Category> findById(String id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Flux<Category> saveAllCategories(Publisher<Category> publisher) {
		return categoryRepository.saveAll(publisher);
	}

	@Override
	public Mono<Category> saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public Mono<Category> patchCategory(String id, Category category) {
		Category foundCategory = categoryRepository.findById(id).block();

		if(foundCategory.getDescription() == null || !foundCategory.getDescription().equals(category.getDescription())){
			foundCategory.setDescription(category.getDescription());
			return categoryRepository.save(foundCategory);
		}

		return Mono.just(foundCategory);
	}
}
