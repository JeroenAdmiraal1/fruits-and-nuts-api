package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.apimodel.CustomerMapper;
import Jeroen.fruitsandnutsapi.domain.Customer;
import Jeroen.fruitsandnutsapi.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	public static final String LASTNAME = "Bancroft";
	public static final long ID = 1L;

	@Mock
	CustomerRepository customerRepository;

	@InjectMocks
	CustomerServiceImpl customerService;

	@BeforeEach
	void setUp() {
		customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
	}

	@Test
	void getAllCustomers() {
		List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

		Mockito.when(customerRepository.findAll()).thenReturn(customers);

		List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

		assertEquals(3, customerDTOS.size());
	}

	@Test
	void getCustomerByLastname() {
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setLastname(LASTNAME);

		Mockito.when(customerRepository.findByLastname(anyString())).thenReturn(customer);

		CustomerDTO customerDTO = customerService.getCustomerByLastname(LASTNAME);

		assertEquals(LASTNAME, customerDTO.getLastname());
	}

	@Test
	void getCustomerById() {
		Customer customer = new Customer();
		customer.setId(3L);
		customer.setLastname(LASTNAME);

		Mockito.when(customerRepository.getById(anyLong())).thenReturn(customer);

		CustomerDTO customerDTO = customerService.getById(3L);

		assertEquals(LASTNAME, customerDTO.getLastname());
	}
}