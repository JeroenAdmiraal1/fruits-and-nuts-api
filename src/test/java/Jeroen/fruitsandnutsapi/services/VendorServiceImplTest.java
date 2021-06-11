package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;
import Jeroen.fruitsandnutsapi.apimodel.mappers.VendorMapper;
import Jeroen.fruitsandnutsapi.domain.Vendor;
import Jeroen.fruitsandnutsapi.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VendorServiceImplTest {

	public static final String NAME = "Harry";
	public static final long ID = 1L;

	@Mock
	VendorRepository vendorRepository;

	@InjectMocks
	VendorServiceImpl vendorService;

	@BeforeEach
	void setUp() {
		vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
	}

	@Test
	void getAllVendors() {
		List<Vendor> vendorList = Arrays.asList(new Vendor(), new Vendor(), new Vendor());

		Mockito.when(vendorRepository.findAll()).thenReturn(vendorList);

		List<VendorDTO> vendorDTOList = vendorService.getAllVendors();

		assertEquals(3, vendorDTOList.size());
	}

	@Test
	void getById() {
		Vendor vendor = new Vendor();
		vendor.setName(NAME);
		vendor.setId(1L);

		Mockito.when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

		VendorDTO vendorDTO = vendorService.getById(1L);

		assertEquals(NAME, vendorDTO.getName());
	}

	@Test
	void createNewVendor() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);

		Vendor savedVendor = new Vendor();
		savedVendor.setName(NAME);
		savedVendor.setId(1L);

		Mockito.when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

		VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);

		assertEquals(vendorDTO.getName(), savedDto.getName());
		assertEquals("/vendors/1", savedDto.getVendor_url());
	}

	@Test
	void saveVendorByDTO() {
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);

		Vendor savedVendor = new Vendor();
		savedVendor.setName(NAME);
		savedVendor.setId(1L);

		Mockito.when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

		VendorDTO savedDto = vendorService.saveVendorByDTO(1L, vendorDTO);

		assertEquals(vendorDTO.getName(), savedDto.getName());
		assertEquals("/vendors/1", savedDto.getVendor_url());
	}

	@Test
	void deleteVendorById() {
		vendorService.deleteVendorById(1L);

		verify(vendorRepository,times(1)).deleteById(anyLong());
	}
}