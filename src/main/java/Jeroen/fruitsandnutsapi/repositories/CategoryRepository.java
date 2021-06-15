package Jeroen.fruitsandnutsapi.repositories;

import Jeroen.fruitsandnutsapi.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}
