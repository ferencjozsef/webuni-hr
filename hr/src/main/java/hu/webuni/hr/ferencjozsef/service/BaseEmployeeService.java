package hu.webuni.hr.ferencjozsef.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;

@Service
public abstract class BaseEmployeeService implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override	
	public Optional<Employee> findById(long id) {
		return employeeRepository.findById(id);
	}
	
	@Override
	@Transactional
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@Override
	@Transactional
	public Employee update(Employee employee) {
		if (!employeeRepository.existsById(employee.getId()))
			return null;
		return employeeRepository.save(employee);
	}
	
	@Override
	@Transactional
	public void delete(long id) {
		employeeRepository.deleteById(id);
	}
	
	@Override
	public List<Employee> findEmplyeesByExample(Employee example) {
		Specification<Employee> spec = Specification.where(null);

		if (example.getId() != null && example.getId() > 0) {
			spec = spec.and(EmployeeSpecification.hasId(example.getId()));
		}
		
		if (example.getName() != null && StringUtils.hasText(example.getName())) {
			spec = spec.and(EmployeeSpecification.hasName(example.getName()));
		}
		
		if (example.getPosition() != null) {
			spec = spec.and(EmployeeSpecification.hasPositionName(example.getPosition().getName()));
		}
		
		if (example.getSalary() > 0) {
			spec = spec.and(EmployeeSpecification.hasSalary(example.getSalary()));
		}
		
		if (example.getStartDate() != null) {
			spec = spec.and(EmployeeSpecification.hasEntryDate(example.getStartDate()));
		}
		
		if (example.getCompany() != null && StringUtils.hasText(example.getCompany().getName())) {
			spec = spec.and(EmployeeSpecification.hasCompanyName(example.getCompany().getName()));
		}
		
		return employeeRepository.findAll(spec, Sort.by("id"));
	}
	
	@Override
	public List<Employee> findBySalaryGreaterThan(Integer minSalary) {
		return employeeRepository.findBySalaryGreaterThan(minSalary);
	}
	
	@Override
	public List<Employee> findByPosition(String position) {
		return employeeRepository.findByPositionName(position);
	}

	@Override
	public List<Employee> findByNameStartingWithIgnoreCase(String name) {
		return employeeRepository.findByNameStartingWithIgnoreCase(name);		
	}

	@Override
	public List<Employee> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return employeeRepository.findByStartDateBetween(startDate, endDate);
	}
	
}
