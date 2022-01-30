package hu.webuni.hr.ferencjozsef.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.model.PositionDetailsByCompany;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;
import hu.webuni.hr.ferencjozsef.repository.PositionDetailsByCompanyRepository;
import hu.webuni.hr.ferencjozsef.repository.PositionRepository;

@Service
public class SalaryService {
	
	private EmployeeService employeeService;
	private PositionRepository positionRepository;
	private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
	private EmployeeRepository employeeRepository;
	
	public SalaryService(EmployeeService employeeService, PositionRepository positionRepository,
			PositionDetailsByCompanyRepository positionDetailsByCompanyRepository,
			EmployeeRepository employeeRepository) {
		super();
		this.employeeService = employeeService;
		this.positionRepository = positionRepository;
		this.positionDetailsByCompanyRepository = positionDetailsByCompanyRepository;
		this.employeeRepository = employeeRepository;
	}

	public void setNewSalary(Employee employee) {
		int newSalary = employee.getSalary() * (100 + employeeService.getPayRaisePercent(employee)) / 100;
		employee.setSalary(newSalary);
	}
	
	@Transactional
	public void raiseMinSalary(long companyId, String positionName, int minSalary) {
//		positionRepository.findByName(positionName)
//		.forEach(p ->{
//			p.setMinSalary(minSalary);
//			p.getEmployees().forEach(e ->{
//				if(e.getSalary()<minSalary)
//					e.setSalary(minSalary);
//			});
//		});
		
		List<PositionDetailsByCompany> positionDetails = positionDetailsByCompanyRepository.findByPositionNameAndCompanyId(positionName, companyId);
		positionDetails.forEach(pd -> pd.setMinSalary(minSalary));
		positionDetailsByCompanyRepository.updateSalaries(companyId, positionName, minSalary);
	}
}
