package hu.webuni.hr.ferencjozsef.web;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

import hu.webuni.hr.ferencjozsef.dto.EmployeeDto;
import hu.webuni.hr.ferencjozsef.mapper.EmployeeMapper;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;
import hu.webuni.hr.ferencjozsef.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	// Az összes alkalmazott visszaadása
	// Egy query paraméterben megkapott értéknél magasabb fizetésú alkalmazottak visszaadása
	@GetMapping
	public List<EmployeeDto> getAll(@RequestParam(required = false) Integer minSalary){
		List<Employee> employees = null;
		if(minSalary == null) {
			employees = employeeService.findAll();
		} else {
			employees = employeeRepository.findBySalaryGreaterThan(minSalary);
		}
		return employeeMapper.employeesToDtos(employees);
	}

	// Adott id-jú alkalmazott visszaadása
	@GetMapping("/{id}")
	public EmployeeDto getById(@PathVariable long id) {
		Employee employee = employeeService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)); 

		return employeeMapper.employeeToDto(employee);
	}
	
	// Új alkalmazott felvétele
	@PostMapping
	public EmployeeDto createEmployeeDto(@RequestBody @Valid EmployeeDto employeeDto) {
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDto));
		return employeeMapper.employeeToDto(employee);
	}

	// Meglévő alkalmazott módosítása
	@PutMapping("/{id}")
	public EmployeeDto modifyEmployeeDto(@PathVariable long id, @RequestBody @Valid EmployeeDto employeeDto){
		if (employeeService.findById(id) != null) {
			Employee employee = employeeService.update(employeeMapper.dtoToEmployee(employeeDto));
			return employeeMapper.employeeToDto(employee);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	// Meglévő alkalmazott törlése
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id) {
		employeeService.delete(id);
	}

	@PostMapping("/payRaisePercent")
	public int getPayRaisePercent(@RequestBody Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}

	// // Adott beosztású alkalmazottak
	@GetMapping("/positionEmployees")
	public List<EmployeeDto> searchPositionEmployees(@RequestParam(required = true) String position){
		//return employeeMapper.employeesToDtos(employeeRepository.findByPosition(position));
		return null;
	}
	
	// Adott stringgel kezdődő nevű alkalmazottak, kis-/negybetű ne számítoson
	@GetMapping("/nameEmployees")
	public List<EmployeeDto> searchNameEmployees(@RequestParam(required = true) String name){
		return employeeMapper.employeesToDtos(employeeRepository.findByNameStartingWithIgnoreCase(name));
	}
	
	// Két adott dátum között belépő alkalmazottak
	@GetMapping("/dateEmployees")
	public List<EmployeeDto> searchDateEmployees(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate, @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
		return employeeMapper.employeesToDtos(employeeRepository.findByStartDateBetween(startDate,endDate));
	}

	
}