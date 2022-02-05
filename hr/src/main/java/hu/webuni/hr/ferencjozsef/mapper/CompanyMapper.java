package hu.webuni.hr.ferencjozsef.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.ferencjozsef.dto.CompanyDto;
import hu.webuni.hr.ferencjozsef.dto.CompanyTypeDto;
import hu.webuni.hr.ferencjozsef.dto.EmployeeDto;
import hu.webuni.hr.ferencjozsef.dto.PositionDto;
import hu.webuni.hr.ferencjozsef.model.Company;
import hu.webuni.hr.ferencjozsef.model.CompanyType;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.model.Position;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	List<CompanyDto> companiesToDtos(List<Company> companies);
	
	@IterableMapping(qualifiedByName = "summary")
	List<CompanyDto> companiesToSummaryDtos(List<Company> companies);
	
	CompanyDto companyToDto(Company company);

	@Mapping(target = "employees", ignore = true)
	@Named("summary")
	CompanyDto companyToSummaryDto(Company company);

	Company dtoToCompany(CompanyDto companyDto);
	
	List<Company> dtosToCompanies(List<CompanyDto> companies);

	@Mapping(target = "companies", ignore = true)
	CompanyTypeDto companyTypeToDto(CompanyType companyType);
	
	CompanyType dtoToCompanyType(CompanyTypeDto companyTypeDto);
	
	@Mapping(target = "company", ignore = true)
	//@Mapping(target = "position", ignore = true)
	EmployeeDto employeeToDto(Employee employee);

	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);

	@Mapping(target = "employees", ignore = true)
	PositionDto positionToDto(Position position);

	Position dtoToPosition(PositionDto positionDto);
	
}
