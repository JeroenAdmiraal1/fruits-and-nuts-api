package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.CategoryDTO;
import Jeroen.fruitsandnutsapi.apimodel.CategoryListDTO;
import Jeroen.fruitsandnutsapi.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService service;

	public CategoryController(CategoryService service) {
		this.service = service;
	}

	@GetMapping({"","/"})
	public ResponseEntity<CategoryListDTO> getAllCategories(){
		return new ResponseEntity<CategoryListDTO>(new CategoryListDTO(service.getAllCategories()), HttpStatus.OK);
	}

	@GetMapping("/{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name){
		return new ResponseEntity<CategoryDTO>(service.getCategoryByName(name), HttpStatus.OK);
	}
}
