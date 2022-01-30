package hu.webuni.hr.ferencjozsef.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import hu.webuni.hr.ferencjozsef.dto.CompanyDto;
import hu.webuni.hr.ferencjozsef.dto.EmployeeDto;
import hu.webuni.hr.ferencjozsef.mapper.CompanyMapper;
import hu.webuni.hr.ferencjozsef.mapper.EmployeeMapper;
import hu.webuni.hr.ferencjozsef.model.AverageSalaryByPosition;
import hu.webuni.hr.ferencjozsef.model.Company;
import hu.webuni.hr.ferencjozsef.repository.CompanyRepository;
import hu.webuni.hr.ferencjozsef.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	@Autowired
	CompanyService companyService;

	@Autowired
	CompanyMapper companyMapper;

	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	CompanyRepository companyRepository;

	// Az összes cég visszaadása
	@GetMapping
	public List<CompanyDto> getAll(@RequestParam(required = false) Boolean full) {
		List<Company> companies = null;
		Boolean notFull = (full == null || !full);
		if (notFull) {
			companies = companyService.findAll();
			return companyMapper.companiesToSummaryDtos(companies);

		} else {
			companies = companyRepository.findAllWithEmployees();
			return companyMapper.companiesToDtos(companies);
		}
	}

	// Adott id-jú cég visszadása
	@GetMapping("/{id}")
	public CompanyDto getById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
		Company company = companyService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		if (isFull(full)) {
			return companyMapper.companyToDto(company);
		} else {
			return companyMapper.companyToSummaryDto(company);
		}
	}

	private Boolean isFull(Boolean full) {
		return full != null && full;
	}

	// Új cég felvétel
	@PostMapping
	public CompanyDto createCompanyDto(@RequestBody @Valid CompanyDto companyDto) {
		return companyMapper.companyToDto(companyService.save(companyMapper.dtoToCompany(companyDto)));
	}

	// Meglévő cég módosítása
	@PutMapping("/{id}")
	public CompanyDto modifyCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
		companyDto.setId(id);
		Company company = companyService.update(companyMapper.dtoToCompany(companyDto));
		if (company != null) {
			return companyMapper.companyToDto(company);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	// Meglévő cég törlése
	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable long id) {
		companyService.delete(id);
	}

	// új alkalmazott vehető fel
	@PostMapping("/{id}/employees")
	public CompanyDto addNewEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
		try {
			return companyMapper.companyToDto(
					companyService.addNewEmployeeToCompany(id, employeeMapper.dtoToEmployee(employeeDto)));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	// A cég alkalmazottai közül törölhető egy megadott id-jú alkalmazott
	@DeleteMapping("/{id}/employees/{employeeId}")
	public CompanyDto deleteEmployeeFromCompany(@PathVariable long id, @PathVariable long employeeId) {
		try {
			return companyMapper.companyToDto(
					companyService.deleteEmployeeFromCompany(id, employeeId));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	// A cég teljes alkalmazott listája lecserélhető egy másik listára
	@PutMapping("/{id}/employees")
	public CompanyDto replaceAllEmployeesFromCompany(@PathVariable long id, @RequestBody List<EmployeeDto> employees) {
		try {
			return companyMapper.companyToDto(
					companyService.replaceAllEmployeesFromCompany(id, employeeMapper.employeeDtosToEmployees(employees)));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}/salaryStats")
	public List<AverageSalaryByPosition> getSalaryStatsById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
		return companyRepository.findAverageSalariesByPosition(id);
	}
	
}
