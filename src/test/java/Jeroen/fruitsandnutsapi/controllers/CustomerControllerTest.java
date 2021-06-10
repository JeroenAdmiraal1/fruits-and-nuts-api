package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest extends AbstractRestControllerTest{

	@Mock
	CustomerService customerService;

	@InjectMocks
	CustomerController customerController;

	MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	void getAllCustomers() throws Exception{
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Michael");
		customer1.setLastname("Weston");
		customer1.setCustomer_url("/customers/1");

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstname("Sam");
		customer2.setLastname("Axe");
		customer2.setCustomer_url("/customers/2");

		when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

		mockMvc.perform(get("/customers/")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}

	@Test
	void getCustomerById() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Michael");
		customer1.setLastname("Weston");
		customer1.setCustomer_url("/customers/1");

		when(customerService.getById(anyLong())).thenReturn(customer1);

		mockMvc.perform(get("/customers/1")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo("Michael")))
				.andExpect(jsonPath("$.customer_url", equalTo("/customers/1")));
	}

	@Test
	void createNewCustomer() throws Exception{
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Fred");
		customer.setLastname("Flintstone");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomer_url("/customers/1");

		when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

		mockMvc.perform(post("/customers/")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(asJsonString(customer)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstname", equalTo("Fred")))
				.andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo("/customers/1")));
	}

	@Test
	void updateCustomer() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Fred");
		customer.setLastname("Flintstone");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomer_url("/customers/1");

		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		mockMvc.perform(put("/customers/1")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(asJsonString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo("Fred")))
				.andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo("/customers/1")));
	}

	@Test
	void patchCustomer() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Fred");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname("Flintstone");
		returnDTO.setCustomer_url("/customers/1");

		when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		mockMvc.perform(patch("/customers/1")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(asJsonString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo("Fred")))
				.andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo("/customers/1")));
	}
}