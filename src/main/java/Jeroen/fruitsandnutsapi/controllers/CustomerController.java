package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.apimodel.CustomerListDTO;
import Jeroen.fruitsandnutsapi.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping({"","/"})
	public ResponseEntity<CustomerListDTO> getAllCustomers(){
		return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(service.getAllCustomers()), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable String id){
		return new ResponseEntity<CustomerDTO>(service.getById(Long.valueOf(id)), HttpStatus.OK);
	}
}