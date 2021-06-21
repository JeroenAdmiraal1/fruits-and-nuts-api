package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.apimodel.mappers.CustomerMapper;
import Jeroen.fruitsandnutsapi.bootstrap.Bootstrap;
import Jeroen.fruitsandnutsapi.domain.Customer;
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
public class CustomerServiceIntegrationTest {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	VendorRepository vendorRepository;

	CustomerService customerService;

	@BeforeEach
	public void setUp() throws Exception {
		Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository, vendorRepository);
		bootstrap.run();

		customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
	}

	@Test
	public void patchCustomerUpdateFirstName() throws Exception {
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();

		Customer originalCustomer = customerRepository.getById(id);
		assertNotNull(originalCustomer);

		String originalFirstName = originalCustomer.getFirstname();
		String originalLastName = originalCustomer.getLastname();

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(updatedName);

		customerService.patchCustomer(id, customerDTO);

		Customer updatedCustomer = customerRepository.findById(id).get();

		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getFirstname());

		assertNotEquals(originalFirstName, updatedCustomer.getFirstname());
		assertEquals(originalLastName, updatedCustomer.getLastname());
	}

	@Test
	public void patchCustomerUpdateLastName() throws Exception {
		String updatedName = "UpdatedName";
		long id = getCustomerIdValue();

		Customer originalCustomer = customerRepository.getById(id);
		assertNotNull(originalCustomer);

		String originalFirstName = originalCustomer.getFirstname();
		String originalLastName = originalCustomer.getLastname();

		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setLastname(updatedName);

		customerService.patchCustomer(id, customerDTO);

		Customer updatedCustomer = customerRepository.findById(id).get();

		assertNotNull(updatedCustomer);
		assertEquals(updatedName, updatedCustomer.getLastname());
		assertEquals(originalFirstName, updatedCustomer.getFirstname());
		assertNotEquals(originalLastName, updatedCustomer.getLastname());
	}

	private Long getCustomerIdValue(){
		List<Customer> customers = customerRepository.findAll();

		//return first id
		return customers.get(0).getId();
	}


}
