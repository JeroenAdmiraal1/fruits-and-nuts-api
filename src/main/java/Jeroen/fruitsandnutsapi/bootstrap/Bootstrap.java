package Jeroen.fruitsandnutsapi.bootstrap;

import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.domain.Vendor;
import Jeroen.fruitsandnutsapi.repositories.CategoryRepository;
import Jeroen.fruitsandnutsapi.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final VendorRepository vendorRepository;

	public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadCategories();
		loadVendors();
	}

	private void loadVendors() {

		if(vendorRepository.count().block() == 0) {
			Vendor vendor1 = new Vendor();
			vendor1.setId("1");
			vendor1.setFirstName("Joe");
			vendor1.setLastName("Schmoe");
			vendorRepository.save(vendor1).block();

			Vendor vendor2 = new Vendor();
			vendor2.setId("2");
			vendor2.setFirstName("John");
			vendor2.setLastName("Doe");
			vendorRepository.save(vendor2).block();

			System.out.println("Vendors loaded.... " + vendorRepository.count().block());
		}
	}


	private void loadCategories() {

		if(categoryRepository.count().block() == 0) {
			Category fruits = new Category();
			fruits.setDescription("Fruits");

			Category dried = new Category();
			dried.setDescription("Dried");

			Category fresh = new Category();
			fresh.setDescription("Fresh");

			Category exotic = new Category();
			exotic.setDescription("Exotic");

			Category nuts = new Category();
			nuts.setDescription("Nuts");

			categoryRepository.save(fruits).block();
			categoryRepository.save(dried).block();
			categoryRepository.save(fresh).block();
			categoryRepository.save(exotic).block();
			categoryRepository.save(nuts).block();

			System.out.println("Categories loaded.... " + categoryRepository.count().block());
		}
	}
}
