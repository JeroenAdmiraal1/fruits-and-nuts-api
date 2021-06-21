package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;
import Jeroen.fruitsandnutsapi.apimodel.mappers.VendorMapper;
import Jeroen.fruitsandnutsapi.bootstrap.Bootstrap;
import Jeroen.fruitsandnutsapi.domain.Vendor;
import Jeroen.fruitsandnutsapi.repositories.CategoryRepository;
import Jeroen.fruitsandnutsapi.repositories.CustomerRepository;
import Jeroen.fruitsandnutsapi.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VendorServiceIntegrationTest {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	VendorRepository vendorRepository;

	VendorService vendorService;

	@BeforeEach
	public void setUp() throws Exception {
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
		bootstrap.run();

		vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
	}

	@Test
	public void patchVendorUpdateName() throws Exception {
		String updatedName = "UpdatedName";
		long id = getVendorIdValue();

		Vendor originalVendor = vendorRepository.getById(id);
		assertNotNull(originalVendor);

		String originalName = originalVendor.getName();

		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(updatedName);

		vendorService.patchVendor(id, vendorDTO);

		Vendor updatedVendor = vendorRepository.findById(id).get();

		assertNotNull(updatedVendor);
		assertEquals(updatedName, updatedVendor.getName());
		assertNotEquals(originalName, updatedVendor.getName());
	}

	private Long getVendorIdValue(){
		List<Vendor> vendors = vendorRepository.findAll();

		//return first id
		return vendors.get(0).getId();
	}
}
