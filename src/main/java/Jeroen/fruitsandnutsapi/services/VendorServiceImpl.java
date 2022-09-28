package Jeroen.fruitsandnutsapi.services;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;
import Jeroen.fruitsandnutsapi.apimodel.mappers.VendorMapper;
import Jeroen.fruitsandnutsapi.domain.Vendor;
import Jeroen.fruitsandnutsapi.repositories.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

	private VendorMapper vendorMapper;
	private VendorRepository vendorRepository;

	public VendorServiceImpl(final VendorMapper vendorMapper, final VendorRepository vendorRepository) {
		this.vendorMapper = vendorMapper;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public List<VendorDTO> getAllVendors() {
		return vendorRepository
				       .findAll().stream()
				       .map(vendor -> {
				       	VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
				       	vendorDTO.setVendor_url(getVendorUrl(vendor.getId()));
				       	return vendorDTO;
				       })
				       .collect(Collectors.toList());
	}



	@Override
	public VendorDTO getById(final Long id) {

		return vendorRepository
				       .findById(id)
				       .map(vendorMapper::vendorToVendorDTO)
				       .map(vendorDTO -> {
				       	vendorDTO.setVendor_url(getVendorUrl(id));
				       	return vendorDTO;
				       }).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorDTO createNewVendor(final VendorDTO vendorDTO) {
		return saveAndReturnDTO(vendorMapper.vendorDtoToVendor(vendorDTO));
	}

	@Override
	public VendorDTO saveVendorByDTO(final Long id, final VendorDTO vendorDTO) {
		Vendor vendor = vendorMapper.vendorDtoToVendor(vendorDTO);
		vendor.setId(id);
		return saveAndReturnDTO(vendor);
	}

	@Override
	public VendorDTO patchVendor(final Long id, final VendorDTO vendorDTO) {
		return vendorRepository.findById(id).map(vendor -> {

			if(vendorDTO.getName() != null){
				vendor.setName(vendorDTO.getName());
			}

			VendorDTO patchedVendorDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
			patchedVendorDTO.setVendor_url(getVendorUrl(id));

			return patchedVendorDTO;
		}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteVendorById(final Long id) {
		vendorRepository.deleteById(id);
	}

	private String getVendorUrl(final Long id) {
		return "/vendors/" + id;
	}

	private VendorDTO saveAndReturnDTO(final Vendor vendor) {
		Vendor savedVendor = vendorRepository.save(vendor);
		VendorDTO returnDto = vendorMapper.vendorToVendorDTO(savedVendor);
		returnDto.setVendor_url(getVendorUrl(savedVendor.getId()));
		return returnDto;
	}
}
