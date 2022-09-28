package Jeroen.fruitsandnutsapi.bootstrap;

import Jeroen.fruitsandnutsapi.domain.Category;
import Jeroen.fruitsandnutsapi.domain.Customer;
import Jeroen.fruitsandnutsapi.domain.Vendor;
import Jeroen.fruitsandnutsapi.repositories.CategoryRepository;
import Jeroen.fruitsandnutsapi.repositories.CustomerRepository;
import Jeroen.fruitsandnutsapi.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

	private final CategoryRepository categoryRepository;
	private final CustomerRepository customerRepository;
	private final VendorRepository vendorRepository;

	public Bootstrap(final CategoryRepository categoryRepository, final CustomerRepository customerRepository, final VendorRepository vendorRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadCategories();
		loadCustomers();
		loadVendors();
	}

	private void loadVendors() {
		Vendor vendor1 = new Vendor();
		vendor1.setId(1L);
		vendor1.setName("Supermarket");
		vendorRepository.save(vendor1);

		Vendor vendor2 = new Vendor();
		vendor2.setId(2L);
		vendor2.setName("Nuts Inc");
		vendorRepository.save(vendor2);

		System.out.println("Vendors loaded.... " + vendorRepository.count());
	}

	private void loadCustomers() {
		Customer customer1 = new Customer();
		customer1.setId(1L);
		customer1.setFirstname("Michael");
		customer1.setLastname("Bancroft");
		customerRepository.save(customer1);

		Customer customer2 = new Customer();
		customer2.setId(2L);
		customer2.setFirstname("Sam");
		customer2.setLastname("Axe");
		customerRepository.save(customer2);

		System.out.println("Customers loaded.... " + customerRepository.count());

	}

	private void loadCategories() {
		Category fruits = new Category();
		fruits.setName("Fruits");

		Category dried = new Category();
		dried.setName("Dried");

		Category fresh = new Category();
		fresh.setName("Fresh");

		Category exotic = new Category();
		exotic.setName("Exotic");

		Category nuts = new Category();
		nuts.setName("Nuts");

		categoryRepository.save(fruits);
		categoryRepository.save(dried);
		categoryRepository.save(fresh);
		categoryRepository.save(exotic);
		categoryRepository.save(nuts);

		System.out.println("Categories loaded.... " + categoryRepository.count());
	}
}
