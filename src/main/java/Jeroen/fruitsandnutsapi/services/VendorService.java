package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;

import java.util.List;

public interface VendorService {

	List<VendorDTO> getAllVendors();

	VendorDTO getById(final Long id);

	VendorDTO createNewVendor(final VendorDTO vendorDTO);

	VendorDTO saveVendorByDTO(final Long id, final VendorDTO vendorDTO);

	VendorDTO patchVendor(final Long id, final VendorDTO vendorDTO);

	void deleteVendorById(final Long id);
}
