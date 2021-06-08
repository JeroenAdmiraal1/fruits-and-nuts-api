package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.apimodel.CustomerMapper;
import Jeroen.fruitsandnutsapi.domain.Customer;
import Jeroen.fruitsandnutsapi.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
	private CustomerRepository customerRepository;
	private CustomerMapper customerMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream()
				       .map(customer -> {
				       	CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
				       	customerDTO.setCustomer_url("/customers/" + customer.getId());
				       	return customerDTO;
				       })
				       .collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerByLastname(String name) {
		return customerMapper.customerToCustomerDTO(customerRepository.findByLastname(name));
	}

	@Override
	public CustomerDTO getById(Long id) {
		return customerMapper.customerToCustomerDTO(customerRepository.getById(id));
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

		Customer savedCustomer = customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO));
		CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
		returnDto.setCustomer_url("/customers/" + savedCustomer.getId());
		return returnDto;
	}
}
