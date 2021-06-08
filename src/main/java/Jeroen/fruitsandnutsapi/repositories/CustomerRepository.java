package Jeroen.fruitsandnutsapi.repositories;

import Jeroen.fruitsandnutsapi.apimodel.CustomerDTO;
import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByLastname(String name);
}
