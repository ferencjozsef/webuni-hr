package hu.webuni.hr.ferencjozsef.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}
	
	@Override
	public Employee update(Employee employee) {
		if (!employeeRepository.existsById(employee.getId()))
			return null;
		return employeeRepository.save(employee);
	}
	
	@Override
	public void delete(long id) {
		employeeRepository.deleteById(id);
	}
}
