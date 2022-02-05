package hu.webuni.hr.ferencjozsef.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.ferencjozsef.model.AverageSalaryByPosition;
import hu.webuni.hr.ferencjozsef.model.Company;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.repository.CompanyRepository;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Transactional
	public Company save(Company company) {
		return companyRepository.save(company);
	}

	@Transactional
	public Company update(Company company) {
		if (!companyRepository.existsById(company.getId())) {
			return null;
		}
		return companyRepository.save(company);
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}

	public List<Company> findAllWithEmployees(){
		return companyRepository.findAllWithEmployees();
	} 
	
	public Optional<Company> findById(long id) {
		return companyRepository.findById(id);
	}

	public Company findByIdWithEmployee(long id) {
		return companyRepository.findByIdWithEmployee(id);
	}
	
	@Transactional
	public void delete(long id) {
		companyRepository.deleteById(id);
	}

	@Transactional
	public Company addNewEmployeeToCompany(long id, Employee employee) {
		Company company = companyRepository.findById(id).get();
		company.addEmployee(employee);
		employeeRepository.save(employee);
		return company;
	}

	@Transactional
	public Company deleteEmployeeFromCompany(long id, long employeeId) {
		Company company = companyRepository.findById(id).get();
		Employee employee = employeeRepository.findById(employeeId).get();
		employee.setCompany(null);
		company.getEmployees().remove(employee);
		employeeRepository.save(employee);
		return company;
	}

	@Transactional
	public Company replaceAllEmployeesFromCompany(long id, List<Employee> employees) {
		Company company = companyRepository.findById(id).get();
		company.getEmployees().forEach(e -> e.setCompany(null));
		company.getEmployees().clear();
		
		for (Employee employee : employees) {
			company.addEmployee(employee);
			employeeRepository.save(employee);
		}
		
		companyRepository.save(company);
		return company;
	}

	public List<AverageSalaryByPosition> findAverageSalariesByPosition(long companyId) {
		return companyRepository.findAverageSalariesByPosition(companyId);
	}
	
}
