package hu.webuni.hr.ferencjozsef.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.ferencjozsef.dto.EmployeeDto;
import hu.webuni.hr.ferencjozsef.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	
	List<EmployeeDto> employeesToDtos(List<Employee> employees);

	List<Employee> employeeDtosToEmployees(List<EmployeeDto> employeeDtos);

	@Mapping(target = "company", ignore = true)
	@Mapping(target = "position.employees", ignore = true)
	EmployeeDto employeeToDto(Employee employee);

	@InheritInverseConfiguration
	Employee dtoToEmployee(EmployeeDto employeeDto);
	
}
