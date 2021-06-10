package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.CategoryDTO;
import Jeroen.fruitsandnutsapi.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

	public static final String NAME = "Lime";

	@Mock
	CategoryService categoryService;
	@InjectMocks
	CategoryController categoryController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	void getAllCategories() throws Exception {
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1l);
		category1.setName(NAME);

		CategoryDTO category2 = new CategoryDTO();
		category2.setId(2l);
		category2.setName("Orange");

		List<CategoryDTO> categories = Arrays.asList(category1, category2);

		when(categoryService.getAllCategories()).thenReturn(categories);

		mockMvc.perform(get(CategoryController.BASE_URL)
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categoryDTOList", hasSize(2)));
	}

	@Test
	void getCategoryByName() throws Exception {
		CategoryDTO category1 = new CategoryDTO();
		category1.setId(1l);
		category1.setName(NAME);

		when(categoryService.getCategoryByName(anyString())).thenReturn(category1);

		mockMvc.perform(get(CategoryController.BASE_URL + "/Lime")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME)));
	}
}