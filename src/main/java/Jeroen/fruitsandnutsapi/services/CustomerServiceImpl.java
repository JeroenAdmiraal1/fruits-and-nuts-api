package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.apimodel.mappers.CustomerMapper;
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
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerRepository.getById(id));
		customerDTO.setCustomer_url("/customers/" + id);
		return customerDTO;
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
		return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
	}

	@Override
	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
		customer.setId(id);
		return saveAndReturnDTO(customer);
	}

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).map(customer -> {

			if(customerDTO.getFirstname() != null){
				customer.setFirstname(customerDTO.getFirstname());
			}

			if(customerDTO.getLastname() != null){
				customer.setLastname(customerDTO.getLastname());
			}

			Customer patchedCustomer = customerRepository.save(customer);
			return customerMapper.customerToCustomerDTO(patchedCustomer);
		}).orElseThrow(RuntimeException::new);
	}


	private CustomerDTO saveAndReturnDTO(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);
		CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
		returnDto.setCustomer_url("/customers/" + savedCustomer.getId());
		return returnDto;
	}
}
