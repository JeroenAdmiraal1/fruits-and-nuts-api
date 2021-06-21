package Jeroen.fruitsandnutsapi.apimodel.mappers;


import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;
import Jeroen.fruitsandnutsapi.domain.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VendorMapper {

	VendorMapper INSTANCE = Mappers.getMapper(VendorMapper.class);

	VendorDTO vendorToVendorDTO(Vendor vendor);

	Vendor vendorDtoToVendor(VendorDTO vendorDTO);
}
