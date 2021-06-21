package Jeroen.fruitsandnutsapi.apimodel.mappers;

import Jeroen.fruitsandnutsapi.apimodel.CategoryDTO;
import Jeroen.fruitsandnutsapi.domain.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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