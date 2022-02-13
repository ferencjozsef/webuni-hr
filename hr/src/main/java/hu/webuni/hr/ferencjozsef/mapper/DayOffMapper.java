package hu.webuni.hr.ferencjozsef.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.ferencjozsef.dto.DayOffDto;
import hu.webuni.hr.ferencjozsef.model.DayOff;

@Mapper(componentModel = "spring")
public interface DayOffMapper {

	@Mapping(source = "employee.id", target = "employeeId")
	@Mapping(source = "approver.id", target = "approverId")
	DayOffDto dayOffToDto(DayOff dayOff);
	
	@Mapping(target = "employee", ignore = true)
	@Mapping(target = "approver", ignore = true)
	DayOff dtoToDayOff(DayOffDto dayOffDto);
	
	List<DayOffDto> dayOffsToDtos(List<DayOff> dayOffs);
	
	List<DayOff> dtosToDayOffs(List<DayOffDto> dayOffDtos);
}
