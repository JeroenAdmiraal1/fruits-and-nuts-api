package Jeroen.fruitsandnutsapi.apimodel;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VendorListDTO {

	private List<VendorDTO> vendorDTOList;
}
