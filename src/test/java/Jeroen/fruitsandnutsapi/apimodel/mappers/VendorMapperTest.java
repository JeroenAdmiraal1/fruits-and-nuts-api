package Jeroen.fruitsandnutsapi.apimodel.mappers;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;
import Jeroen.fruitsandnutsapi.domain.Vendor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendorMapperTest {

	public static final String NAME = "Harry";
	VendorMapper vendorMapper = VendorMapper.INSTANCE;

	@Test
	void vendorToVendorDTO() {
		Vendor vendor = new Vendor();
		vendor.setName(NAME);
		vendor.setId(1L);

		VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

		assertEquals(Long.valueOf(1), vendorDTO.getId());
		assertEquals(NAME, vendorDTO.getName());

	}
}