package Jeroen.fruitsandnutsapi.repositories;

import Jeroen.fruitsandnutsapi.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
