package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.apimodel.mappers.CustomerMapper;
import Jeroen.fruitsandnutsapi.controllers.CustomerController;
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
				       	customerDTO.setCustomer_url(getCustomerUrl(customer.getId()));
				       	return customerDTO;
				       })
				       .collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getById(Long id) {

		return customerRepository.findById(id)
				       .map(customerMapper::customerToCustomerDTO)
				       .map(customerDTO -> {
				       	customerDTO.setCustomer_url(getCustomerUrl(id));
				       	return customerDTO;
				       }).orElseThrow(ResourceNotFoundException::new);
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

			CustomerDTO patchedCustomerDTO = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
			patchedCustomerDTO.setCustomer_url(getCustomerUrl(id));

			return patchedCustomerDTO;
		}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}


	private CustomerDTO saveAndReturnDTO(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);
		CustomerDTO returnDto = customerMapper.customerToCustomerDTO(savedCustomer);
		returnDto.setCustomer_url(getCustomerUrl(savedCustomer.getId()));
		return returnDto;
	}

	private String getCustomerUrl(Long id) {
		return CustomerController.BASE_URL + "/" + id;
	}
}
