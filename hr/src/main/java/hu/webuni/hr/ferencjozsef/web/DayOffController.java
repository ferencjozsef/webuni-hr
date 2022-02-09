package hu.webuni.hr.ferencjozsef.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.ferencjozsef.dto.DayOffDto;
import hu.webuni.hr.ferencjozsef.dto.SearchDayOffDto;
import hu.webuni.hr.ferencjozsef.mapper.DayOffMapper;
import hu.webuni.hr.ferencjozsef.model.DayOff;
import hu.webuni.hr.ferencjozsef.service.DayOffService;

@RestController
@RequestMapping("/api/dayoff")
public class DayOffController {

	@Autowired
	DayOffService dayOffService;

	@Autowired
	DayOffMapper dayOffMapper;


	@GetMapping
	public List<DayOffDto> getAll(@SortDefault("id") Pageable pageable) {
		Page<DayOff> page = dayOffService.findAll(pageable);
		System.out.println(page.getTotalElements());
		System.out.println(page.isLast());
		List<DayOff> allDayOffs = page.getContent();
		return dayOffMapper.dayOffsToDtos(allDayOffs);
	}

	@PostMapping
	public DayOffDto createDayOff(@RequestBody DayOffDto dayOffDto) {
		return dayOffMapper.dayOffToDto(dayOffService.save(dayOffMapper.dtoToDayOff(dayOffDto)));
	}

	@PutMapping("/{id}/approve")
	public DayOffDto dayOffApprove(@PathVariable long id, @RequestBody DayOffDto dayOffDto) {
		dayOffDto.setId(id);
		return dayOffMapper.dayOffToDto(dayOffService.aprove(dayOffMapper.dtoToDayOff(dayOffDto)));
	}

	@PutMapping("/{id}/employee")
	public DayOffDto dayOffUpdate(@PathVariable long id, @RequestBody DayOffDto dayOffDto) {
		dayOffDto.setId(id);
		return dayOffMapper.dayOffToDto(dayOffService.update(dayOffMapper.dtoToDayOff(dayOffDto)));
	}
	
	@DeleteMapping("/{id}")
	public void deleteDayOff(@PathVariable long id) {
		dayOffService.delete(id);
	}
	
	@GetMapping("/searchDayOff")
	public List<DayOffDto> searchDayOff(@RequestBody SearchDayOffDto searchDayOffDto) {
		return dayOffMapper.dayOffsToDtos(dayOffService.findDayOffByExample(searchDayOffDto.getApproved(), 
				searchDayOffDto.getCreateUser(), searchDayOffDto.getBoss(),
				searchDayOffDto.getCreateDateStart(),searchDayOffDto.getCreateDateEnd(),
				searchDayOffDto.getStartDayOffDate(),searchDayOffDto.getCreateDateEnd()));
	}	
}
