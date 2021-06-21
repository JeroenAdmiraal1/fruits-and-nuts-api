package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;
import Jeroen.fruitsandnutsapi.services.ResourceNotFoundException;
import Jeroen.fruitsandnutsapi.services.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest extends AbstractRestControllerTest{

	@Mock
	VendorService vendorService;

	@InjectMocks
	VendorController vendorController;

	MockMvc mockMvc;

	VendorDTO vendorDTO1;
	VendorDTO returnedDTO;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders
				          .standaloneSetup(vendorController)
				          .setControllerAdvice(new RestResponseEntityExceptionHandler()).build();

	  vendorDTO1 = new VendorDTO();
		vendorDTO1.setName("Supermarket");
		vendorDTO1.setId(1L);
		vendorDTO1.setVendor_url(VendorController.BASE_URL + "/1");

		returnedDTO = new VendorDTO();
		returnedDTO.setName("Supermarket");
		returnedDTO.setId(2L);
		returnedDTO.setVendor_url(VendorController.BASE_URL + "/2");
	}

	@Test
	void getAllVendors() throws Exception {

		Mockito.when(vendorService.getAllVendors()).thenReturn(Arrays.asList(vendorDTO1, returnedDTO));

		mockMvc.perform(get(VendorController.BASE_URL)
				                .accept(MediaType.APPLICATION_JSON)
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.vendorDTOList", hasSize(2)));
	}

	@Test
	void getById() throws Exception {

		Mockito.when(vendorService.getById(anyLong())).thenReturn(vendorDTO1);

		mockMvc.perform(get(VendorController.BASE_URL + "/1")
				                .accept(MediaType.APPLICATION_JSON)
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo("Supermarket")))
				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}

	@Test
	void createNewVendor() throws Exception{

		Mockito.when(vendorService.createNewVendor(vendorDTO1)).thenReturn(returnedDTO);

		mockMvc.perform(post(VendorController.BASE_URL)
				                .accept(MediaType.APPLICATION_JSON)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(asJsonString(vendorDTO1)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", equalTo("Supermarket")))
				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/2")));
	}

	@Test
	void updateVendor() throws Exception {

		when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnedDTO);

		mockMvc.perform(put(VendorController.BASE_URL + "/1")
				                .accept(MediaType.APPLICATION_JSON)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(asJsonString(vendorDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo("Supermarket")))
				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/2")));
	}

	@Test
	void patchVendor() throws Exception {

		when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnedDTO);

		mockMvc.perform(patch(VendorController.BASE_URL + "/1")
				                .accept(MediaType.APPLICATION_JSON)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(asJsonString(vendorDTO1)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo("Supermarket")))
				.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/2")));
	}

	@Test
	void deleteVendor() throws Exception {
		mockMvc.perform(delete(VendorController.BASE_URL + "/1")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(vendorService).deleteVendorById(anyLong());
	}

	@Test
	public void testNotFoundException() throws Exception {

		when(vendorService.getById(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(VendorController.BASE_URL + "/222")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}



}