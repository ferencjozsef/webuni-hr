package hu.webuni.hr.ferencjozsef.web;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.ferencjozsef.dto.DayOffDto;
import hu.webuni.hr.ferencjozsef.dto.SearchDayOffDto;
import hu.webuni.hr.ferencjozsef.mapper.DayOffMapper;
import hu.webuni.hr.ferencjozsef.model.DayOff;
import hu.webuni.hr.ferencjozsef.service.DayOffService;

@RestController
@RequestMapping("/api/dayoffs")
public class DayOffController {

	@Autowired
	DayOffService dayOffService;

	@Autowired
	DayOffMapper dayOffMapper;


	@GetMapping
	public List<DayOffDto> getAll(@SortDefault("id") Pageable pageable) {
		Page<DayOff> page = dayOffService.findAll(pageable);
		List<DayOff> allDayOffs = page.getContent();
		return dayOffMapper.dayOffsToDtos(allDayOffs);
	}
	
	@GetMapping("/{id}")
	public DayOffDto getById(@PathVariable long id) {
		DayOff dayOff = dayOffService.findById(id).orElseThrow( 
													() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		return dayOffMapper.dayOffToDto(dayOff);
	}

	@GetMapping("/searchDayOff")
	public List<DayOffDto> searchDayOff(@RequestBody SearchDayOffDto searchDayOffDto, Pageable pageable) {
		Page<DayOff> page = dayOffService.findDayOffByExample(searchDayOffDto, pageable);
		return dayOffMapper.dayOffsToDtos(page.getContent());
	}

	
	@PostMapping
	@PreAuthorize("#dayOffDto.employeeId == authentication.principal.employee.id")
	public DayOffDto createDayOff(@RequestBody @Valid DayOffDto dayOffDto) {
		DayOff dayOff;
		try {
			dayOff = dayOffService.addDayOff(dayOffMapper.dtoToDayOff(dayOffDto), dayOffDto.getEmployeeId());
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return dayOffMapper.dayOffToDto(dayOff);
	}

	@DeleteMapping("/{id}")
	public void deleteDayOff(@PathVariable long id) {
		dayOffService.delete(id);
	}
	

	@PutMapping("/{id}")
	public DayOffDto updateDayOff(@PathVariable long id, @RequestBody @Valid DayOffDto dayOffDto) {
		dayOffDto.setEmployeeId(id);
		DayOff dayOff;
		try {
			dayOff = dayOffService.modifyDayOff(id, dayOffMapper.dtoToDayOff(dayOffDto));
		} catch (InvalidParameterException e) {
			throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return dayOffMapper.dayOffToDto(dayOff);
	}


	@PutMapping(value = "/{id}/approval", params = {"status", "approverId"})
	@PreAuthorize("#approverId == authentication.principal.employee.id")
	public DayOffDto dayOffApprove(@PathVariable long id, @RequestParam long approverId, @RequestParam boolean status) {
		DayOff dayOff;
		try {
			dayOff = dayOffService.approveDayOff(id, approverId, status);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		
		return dayOffMapper.dayOffToDto(dayOff);
	}


}
