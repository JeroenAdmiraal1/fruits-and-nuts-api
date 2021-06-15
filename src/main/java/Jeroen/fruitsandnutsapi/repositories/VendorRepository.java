package Jeroen.fruitsandnutsapi.repositories;

import Jeroen.fruitsandnutsapi.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
