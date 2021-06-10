package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;

import java.util.List;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();

	CustomerDTO getCustomerByLastname(String name);

	CustomerDTO getById(Long id);

	CustomerDTO createNewCustomer(CustomerDTO customerDTO);

	CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);

	CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
}
