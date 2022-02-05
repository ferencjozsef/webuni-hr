package hu.webuni.hr.ferencjozsef.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import hu.webuni.hr.ferencjozsef.model.Employee;

public interface EmployeeService {
	
	public int getPayRaisePercent(Employee employee);
	
	public List<Employee> findAll();
	
	public Optional<Employee> findById(long id);
	
	public Employee save(Employee employee);
	
	public Employee update(Employee employee);
	
	public void delete(long id);
	
	public List<Employee> findEmplyeesByExample(Employee example);

	public List<Employee> findBySalaryGreaterThan(Integer minSalary);

	public List<Employee> findByPosition(String position);

	public List<Employee> findByNameStartingWithIgnoreCase(String name);

	public List<Employee> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
