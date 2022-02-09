package hu.webuni.hr.ferencjozsef.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.ferencjozsef.dto.DayOffDto;
import hu.webuni.hr.ferencjozsef.model.DayOff;

@Mapper(componentModel = "spring")
public interface DayOffMapper {

	DayOffDto dayOffToDto(DayOff dayOff);
	
	DayOff dtoToDayOff(DayOffDto dayOffDto);
	
	List<DayOffDto> dayOffsToDtos(List<DayOff> dayOffs);
	
	List<DayOff> dtosToDayOffs(List<DayOffDto> dayOffDtos);
}
