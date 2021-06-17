package Jeroen.fruitsandnutsapi.controllers;

import Jeroen.fruitsandnutsapi.domain.Vendor;
import Jeroen.fruitsandnutsapi.repositories.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {

	WebTestClient webTestClient;

	@Mock
	VendorRepository vendorRepository;

	@InjectMocks
	VendorController vendorController;

	@BeforeEach
	void setUp() {
		webTestClient = WebTestClient.bindToController(vendorController).build();

		vendorController = new VendorController(vendorRepository);
	}

	@Test
	void listOfVendors() {

		BDDMockito.given(vendorRepository.findAll())
				.willReturn(Flux.just(
						Vendor.builder().firstName("Joe").build(),
						Vendor.builder().firstName("John").build()));

		webTestClient.get()
				.uri("/vendors").exchange()
				.expectStatus().isOk()
				.expectBodyList(Vendor.class).hasSize(2);
	}

	@Test
	void getById() {

		BDDMockito.given(vendorRepository.findById(anyString()))
				.willReturn(Mono.just(Vendor.builder().firstName("Joe").build()));

		webTestClient.get()
				.uri("/vendors/1").exchange()
				.expectStatus().isOk()
				.expectBody(Vendor.class);
	}
}