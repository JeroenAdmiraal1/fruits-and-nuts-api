package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;
import Jeroen.fruitsandnutsapi.apimodel.VendorListDTO;
import Jeroen.fruitsandnutsapi.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

	public static final String BASE_URL = "/vendors";

	private final VendorService service;

	public VendorController(VendorService service) {
		this.service = service;
	}

	@GetMapping({"","/"})
	@ResponseStatus(HttpStatus.OK)
	public VendorListDTO getAllVendors(){
		return new VendorListDTO(service.getAllVendors());
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable String id){
		return service.getById(Long.valueOf(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO){
		return service.createNewVendor(vendorDTO);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO updateVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO){
		return service.saveVendorByDTO(Long.valueOf(id), vendorDTO);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable String id, @RequestBody VendorDTO vendorDTO){
		return service.patchVendor(Long.valueOf(id), vendorDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteVendor(@PathVariable String id){
		service.deleteVendorById(Long.valueOf(id));
	}
}
