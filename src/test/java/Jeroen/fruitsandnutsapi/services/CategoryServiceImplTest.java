package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CategoryDTO;
import Jeroen.fruitsandnutsapi.apimodel.mappers.CategoryMapper;
import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

	public static final String NAME = "Lime";
	public static final long ID = 1L;
	@Mock
	private CategoryRepository categoryRepository;
	@InjectMocks
	private CategoryServiceImpl categoryService;

	@BeforeEach
	public void setUp() {
		categoryService = new CategoryServiceImpl(categoryRepository, CategoryMapper.INSTANCE);
	}

	@Test
	void getAllCategories() {

		List<Category> categoryList = Arrays.asList(new Category(), new Category(), new Category());

		Mockito.when(categoryRepository.findAll()).thenReturn(categoryList);

		List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();

		assertEquals(3, categoryDTOList.size());
	}

	@Test
	void getCategoryByName() {

		Category category = new Category();
		category.setName(NAME);
		category.setId(ID);

		Mockito.when(categoryRepository.findByName(anyString())).thenReturn(category);

		CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

		assertEquals(ID, categoryDTO.getId());
		assertEquals(NAME, categoryDTO.getName());
	}
}