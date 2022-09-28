package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;

import java.util.List;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();

	CustomerDTO getById(final Long id);

	CustomerDTO createNewCustomer(final CustomerDTO customerDTO);

	CustomerDTO saveCustomerByDTO(final Long id, final CustomerDTO customerDTO);

	CustomerDTO patchCustomer(final Long id, final CustomerDTO customerDTO);

	void deleteCustomerById(final Long id);
}
