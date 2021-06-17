package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.domain.Vendor;
import Jeroen.fruitsandnutsapi.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/vendors")
	Mono<Void> createVendors(@RequestBody Publisher<Vendor> vendorPublisher) {
		return vendorRepository.saveAll(vendorPublisher).then();
	}

	@PutMapping("/vendors/{id}")
	Mono<Vendor> updateVendor(@PathVariable String id, @RequestBody Vendor vendor) {
		vendor.setId(id);
		return vendorRepository.save(vendor);
	}

	@PatchMapping("/vendors/{id}")
	Mono<Vendor> patchVendor(@PathVariable String id, @RequestBody Vendor vendor) {

		Vendor foundVendor = vendorRepository.findById(id).block();

		boolean changesAreMade = false;

		if(foundVendor.getFirstName() == null || !foundVendor.getFirstName().equals(vendor.getFirstName())){
			foundVendor.setFirstName(vendor.getFirstName());
			changesAreMade = true;
		}

		if(foundVendor.getLastName() == null || !foundVendor.getLastName().equals(vendor.getLastName())){
			foundVendor.setLastName(vendor.getLastName());
			changesAreMade = true;
		}

		if(changesAreMade){
			return vendorRepository.save(foundVendor);
		}
		return Mono.just(foundVendor);
	}
}
