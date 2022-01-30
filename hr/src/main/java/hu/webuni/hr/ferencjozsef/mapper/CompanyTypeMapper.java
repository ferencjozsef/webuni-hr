package hu.webuni.hr.ferencjozsef.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.ferencjozsef.dto.CompanyTypeDto;
import hu.webuni.hr.ferencjozsef.model.CompanyType;

@Mapper(componentModel = "spring")
public interface CompanyTypeMapper {

	@Mapping(target = "companies", ignore = true)
	CompanyTypeDto companyTypeToDto(CompanyType companyType);
	
	CompanyType dtoToCompanyType(CompanyTypeDto companyTypeDto);
	
	List<CompanyTypeDto> companyTypesToDtos(List<CompanyType> companyTypes);
	
	List<CompanyType> dtosToCompanyTypes(List<CompanyTypeDto> companyTypeDtos);
}
