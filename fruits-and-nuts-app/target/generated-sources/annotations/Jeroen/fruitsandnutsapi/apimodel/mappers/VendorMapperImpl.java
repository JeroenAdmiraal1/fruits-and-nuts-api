package Jeroen.fruitsandnutsapi.apimodel.mappers;

import Jeroen.fruitsandnutsapi.apimodel.VendorDTO;
import Jeroen.fruitsandnutsapi.domain.Vendor;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-06-21T11:16:53+0200",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.10 (AdoptOpenJDK)"
)
@Component
public class VendorMapperImpl implements VendorMapper {

    @Override
    public VendorDTO vendorToVendorDTO(Vendor vendor) {
        if ( vendor == null ) {
            return null;
        }

        VendorDTO vendorDTO = new VendorDTO();

        vendorDTO.setId( vendor.getId() );
        vendorDTO.setName( vendor.getName() );

        return vendorDTO;
    }

    @Override
    public Vendor vendorDtoToVendor(VendorDTO vendorDTO) {
        if ( vendorDTO == null ) {
            return null;
        }

        Vendor vendor = new Vendor();

        vendor.setId( vendorDTO.getId() );
        vendor.setName( vendorDTO.getName() );

        return vendor;
    }
}
