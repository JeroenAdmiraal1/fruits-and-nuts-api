package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;

import java.util.List;

public interface VendorService {

	List<VendorDTO> getAllVendors();

	VendorDTO getById(Long id);

	VendorDTO createNewVendor(VendorDTO vendorDTO);

	VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);

	VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

	void deleteVendorById(Long id);
}
