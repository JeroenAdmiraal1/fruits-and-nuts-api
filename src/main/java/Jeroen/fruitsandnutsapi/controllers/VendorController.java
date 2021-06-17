package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.domain.Vendor;
import Jeroen.fruitsandnutsapi.repositories.VendorRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class VendorController {

	private VendorRepository vendorRepository;

	public VendorController(VendorRepository vendorRepository) {
		this.vendorRepository = vendorRepository;
	}

	@GetMapping("/vendors")
	Flux<Vendor> listOfVendors() {
		return vendorRepository.findAll();
	}

	@GetMapping("/vendors/{id}")
	Mono<Vendor> getById(@PathVariable String id){
		return vendorRepository.findById(id);
	}
}
