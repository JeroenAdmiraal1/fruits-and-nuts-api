package Jeroen.fruitsandnutsapi.apimodel;

import Jeroen.fruitsandnutsapi.apimodel.mappers.CategoryMapper;
import Jeroen.fruitsandnutsapi.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

	public static final String NAME = "Lime";
	public static final long ID = 1L;
	CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

	@Test
	void categoryToCategoryDTO() {
		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);

		CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

		assertEquals(Long.valueOf(1), categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}
}