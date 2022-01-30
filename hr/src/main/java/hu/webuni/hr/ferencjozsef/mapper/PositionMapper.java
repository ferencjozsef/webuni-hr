package hu.webuni.hr.ferencjozsef.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import hu.webuni.hr.ferencjozsef.dto.PositionDto;
import hu.webuni.hr.ferencjozsef.model.Position;


@Mapper(componentModel = "spring")
public interface PositionMapper {
	
	@Mapping(target = "employees", ignore = true)
	PositionDto positionToDto(Position position);

	Position dtoToPosition(PositionDto positionDto);
	
	List<PositionDto> positionsToDtos(List<Position> positions);

	List<Position> dtosToPositions(List<PositionDto> positionDtos);
	
}
