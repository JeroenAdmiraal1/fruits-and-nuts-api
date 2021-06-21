package Jeroen.fruitsandnutsapi.apimodel.mappers;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.domain.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerMapperTest {

	public static final String LASTNAME = "Bancroft";
	public static final long ID = 1L;
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	@Test
	void customerToCustomerDTO() {
		Customer customer = new Customer();
		customer.setLastname(LASTNAME);
		customer.setId(ID);

		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

		assertEquals(LASTNAME, customerDTO.getLastname());
	}

	@Test
	void customerDtoToCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLastname(LASTNAME);

		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

		assertEquals(LASTNAME, customer.getLastname());
	}
}