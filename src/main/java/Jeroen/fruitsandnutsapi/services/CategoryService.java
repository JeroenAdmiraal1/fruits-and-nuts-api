package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CategoryDTO;

import java.util.List;

public interface CategoryService {

	List<CategoryDTO> getAllCategories();

	CategoryDTO getCategoryByName(final String name);
}
