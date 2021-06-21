package Jeroen.fruitsandnutsapi.apimodel.mappers;

import Jeroen.fruitsandnutsapi.apimodel.CategoryDTO;
import Jeroen.fruitsandnutsapi.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	CategoryDTO categoryToCategoryDTO(Category category);
}
