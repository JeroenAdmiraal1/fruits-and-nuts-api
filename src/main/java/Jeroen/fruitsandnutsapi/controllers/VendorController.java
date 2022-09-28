package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;
import Jeroen.fruitsandnutsapi.apimodel.VendorListDTO;
import Jeroen.fruitsandnutsapi.services.VendorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(VendorController.BASE_URL)
public class VendorController {

	public static final String BASE_URL = "/vendors";

	private final VendorService service;

	public VendorController(final VendorService service) {
		this.service = service;
	}

	@GetMapping({"","/"})
	@ResponseStatus(HttpStatus.OK)
	public VendorListDTO getAllVendors(){
		return new VendorListDTO(service.getAllVendors());
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable final String id){
		return service.getById(Long.valueOf(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody final VendorDTO vendorDTO){
		return service.createNewVendor(vendorDTO);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO updateVendor(@PathVariable final String id, @RequestBody final VendorDTO vendorDTO){
		return service.saveVendorByDTO(Long.valueOf(id), vendorDTO);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable final String id, @RequestBody final VendorDTO vendorDTO){
		return service.patchVendor(Long.valueOf(id), vendorDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteVendor(@PathVariable final String id){
		service.deleteVendorById(Long.valueOf(id));
	}
}
