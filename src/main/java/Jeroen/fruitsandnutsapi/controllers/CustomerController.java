package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.apimodel.CustomerListDTO;
import Jeroen.fruitsandnutsapi.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(CustomerController.BASE_URL)
public class CustomerController {

	public static final String BASE_URL = "/customers";
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

	@PostMapping
	public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<CustomerDTO>(service.createNewCustomer(customerDTO), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<CustomerDTO>(service.saveCustomerByDTO(Long.valueOf(id), customerDTO), HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<CustomerDTO> patchCustomer(@PathVariable String id, @RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<CustomerDTO>(service.patchCustomer(Long.valueOf(id), customerDTO), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable String id){
		service.deleteCustomerById(Long.valueOf(id));
		return new ResponseEntity<Void>(HttpStatus.OK);
	}


}
