package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.CategoryDTO;
import Jeroen.fruitsandnutsapi.apimodel.CategoryListDTO;
import Jeroen.fruitsandnutsapi.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

	public static final String BASE_URL = "/categories";
	private final CategoryService service;

	public CategoryController(final CategoryService service) {
		this.service = service;
	}

	@GetMapping({"","/"})
	@ResponseStatus(HttpStatus.OK)
	public CategoryListDTO getAllCategories(){
		return new CategoryListDTO(service.getAllCategories());
	}

	@GetMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public CategoryDTO getCategoryByName(@PathVariable String name){
		return service.getCategoryByName(name);
	}
}
