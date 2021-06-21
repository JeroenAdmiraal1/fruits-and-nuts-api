package Jeroen.fruitsandnutsapi.repositories;

import Jeroen.fruitsandnutsapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
