package Jeroen.fruitsandnutsapi.repositories;

import Jeroen.fruitsandnutsapi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(final String name);
}
