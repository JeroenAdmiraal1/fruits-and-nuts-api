package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.services.CustomerService;
import Jeroen.fruitsandnutsapi.services.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(CustomerController.class)
class CustomerControllerWebMvcTest extends AbstractRestControllerTest{

	@MockBean
	CustomerService customerService;


	@Autowired
	MockMvc mockMvc;

	@Test
	void getAllCustomers() throws Exception{
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Michael");
		customer1.setLastname("Weston");
		customer1.setCustomer_url(CustomerController.BASE_URL + "/1");

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstname("Sam");
		customer2.setLastname("Axe");
		customer2.setCustomer_url(CustomerController.BASE_URL + "/2");

		when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

		mockMvc.perform(get(CustomerController.BASE_URL)
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}

	@Test
	void getCustomerById() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname("Michael");
		customer1.setLastname("Weston");
		customer1.setCustomer_url(CustomerController.BASE_URL + "/1");

		when(customerService.getById(anyLong())).thenReturn(customer1);

		mockMvc.perform(get(CustomerController.BASE_URL + "/1")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo("Michael")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	}

	@Test
	void createNewCustomer() throws Exception{
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Fred");
		customer.setLastname("Flintstone");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomer_url(CustomerController.BASE_URL + "/1");

		when(customerService.createNewCustomer(customer)).thenReturn(returnDTO);

		mockMvc.perform(post(CustomerController.BASE_URL)
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(asJsonString(customer)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstname", equalTo("Fred")))
				.andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")))
				.andDo(document("customers",
						requestFields(
								fieldWithPath("firstname").description("First name of the Customer"),
								fieldWithPath("lastname").description("Last name of the Customer"),
								fieldWithPath("customer_url").ignored()
						)));
	}

	@Test
	void updateCustomer() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Fred");
		customer.setLastname("Flintstone");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname(customer.getLastname());
		returnDTO.setCustomer_url(CustomerController.BASE_URL + "/1");

		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		mockMvc.perform(put(CustomerController.BASE_URL + "/1")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(asJsonString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo("Fred")))
				.andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	}

	@Test
	void patchCustomer() throws Exception {
		CustomerDTO customer = new CustomerDTO();
		customer.setFirstname("Fred");

		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customer.getFirstname());
		returnDTO.setLastname("Flintstone");
		returnDTO.setCustomer_url(CustomerController.BASE_URL + "/1");

		when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(asJsonString(customer)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo("Fred")))
				.andExpect(jsonPath("$.lastname", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	}

	@Test
	void deleteCustomer() throws Exception {
		mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(customerService).deleteCustomerById(anyLong());
	}

	@Test
	public void testNotFoundException() throws Exception {

		when(customerService.getById(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(CustomerController.BASE_URL + "/222")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}