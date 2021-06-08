package Jeroen.fruitsandnutsapi.apimodel;

import Jeroen.fruitsandnutsapi.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	CustomerDTO customerToCustomerDTO(Customer customer);

	Customer customerDtoToCustomer(CustomerDTO customerDTO);
}
