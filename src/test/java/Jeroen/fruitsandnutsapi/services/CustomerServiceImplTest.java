package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.apimodel.mappers.CustomerMapper;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

	@Test
	void createNewCustomer() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("Jim");

		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname("Jim");
		savedCustomer.setId(1L);

		Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

		CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

		assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
		assertEquals("/customers/1", savedDto.getCustomer_url());

	}

	@Test
	void saveCustomerByDTO() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("Jim");

		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname("Jim");
		savedCustomer.setId(1L);

		Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

		CustomerDTO savedDTO = customerService.saveCustomerByDTO(1L, customerDTO);

		assertEquals(customerDTO.getFirstname(), savedDTO.getFirstname());
		assertEquals("/customers/1", savedDTO.getCustomer_url());
	}

	@Test
	void deleteCustomerById() {

		customerService.deleteCustomerById(1L);

		verify(customerRepository,times(1)).deleteById(anyLong());
	}
}