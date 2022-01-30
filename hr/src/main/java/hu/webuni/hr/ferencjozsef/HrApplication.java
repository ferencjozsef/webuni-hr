package hu.webuni.hr.ferencjozsef;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import hu.webuni.hr.ferencjozsef.model.Company;
import hu.webuni.hr.ferencjozsef.model.CompanyType;
import hu.webuni.hr.ferencjozsef.repository.CompanyRepository;
import hu.webuni.hr.ferencjozsef.repository.CompanyTypeRepository;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;
import hu.webuni.hr.ferencjozsef.service.InitDbService;
import hu.webuni.hr.ferencjozsef.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	SalaryService salaryService;
	
	@Autowired
	InitDbService initDbService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CompanyTypeRepository companyTypeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		Employee employee1 = new Employee(10L, "employee1", "Főnök", 500_000, LocalDateTime.parse("2010-10-01T12:00:00"));
//		Employee employee2 = new Employee(11L, "employee2", "Aszisztens", 350_000, LocalDateTime.parse("2014-10-01T12:00:00"));
//		Employee employee3 = new Employee(12L, "employee3", "Kereskedő", 300_000, LocalDateTime.parse("2018-10-01T12:00:00"));
//		Employee employee4 = new Employee(13L, "employee4", "Kereskedő", 250_000, LocalDateTime.parse("2020-10-01T12:00:00"));
//		salaryService.setNewSalary(employee1);
//		salaryService.setNewSalary(employee2);
//		salaryService.setNewSalary(employee3);
//		salaryService.setNewSalary(employee4);
//		System.out.println(employee1.getSalary());
//		System.out.println(employee2.getSalary());
//		System.out.println(employee3.getSalary());
//		System.out.println(employee4.getSalary());
		
		initDbService.clearDB();
		initDbService.insertTestData();
		
		List<Company> companiesMinSalary = companyRepository.getCompaniesExistingMinSalaryEmployee(500_000);
		System.out.println("companiesMinSalary: " + companiesMinSalary.size());
		for (Company company : companiesMinSalary) {
			System.out.println(company.toString());
		}

		List<Company> companiesMinLimit = companyRepository.getCompaniesMinLimitHaveEmployee(1);
		System.out.println("companiesMinLimit: " + companiesMinLimit.size());
		for (Company company : companiesMinLimit) {
			System.out.println(company.toString());
		}

//		Pageable firstPageWithTwoElements = PageRequest.of(0, 1);
//		Pageable secondPageWithFiveElements = PageRequest.of(1, 5);
//		
//		List<Company> companiesMinLimit1 = companyRepository.getCompaniesMinLimitHaveEmployee(1L, firstPageWithTwoElements);
//		System.out.println("companiesMinLimit1: " + companiesMinLimit1.size());
//		for (Company company : companiesMinLimit1) {
//			System.out.println(company.toString());
//		}
//
//		List<Company> companiesMinLimit2 = companyRepository.getCompaniesMinLimitHaveEmployee(2L, secondPageWithFiveElements);
//		System.out.println("companiesMinLimit2: " + companiesMinLimit2.size());
//		for (Company company : companiesMinLimit2) {
//			System.out.println(company.toString());
//		}
		
//		Pageable firstPageWithTwoElements = PageRequest.of(0, 2);
//		Pageable secondPageWithThreeElements = PageRequest.of(1, 2);
//		
//		Page<CompanyType> companyTypePage1 = companyTypeRepository.findAll(firstPageWithTwoElements);
//		System.out.println("companyTypePage1: " + companyTypePage1.getSize());		
//		for (CompanyType companyType : companyTypePage1) {
//			System.out.println(companyType.toString());			
//		}
//		
//		Page<CompanyType> companyTypePage2 = companyTypeRepository.findAll(secondPageWithThreeElements);
//		System.out.println("companyTypePage2: " + companyTypePage2.getSize());		
//		for (CompanyType companyType : companyTypePage2) {
//			System.out.println(companyType.toString());			
//		}

		List<CompanyType> allCompanyTypesSort = companyTypeRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
		System.out.println("allCompanyTypesSort: " + allCompanyTypesSort.size());		
		for (CompanyType companyType : allCompanyTypesSort) {
			System.out.println(companyType.toString());			
		}
		
		System.out.println("end run");
		
	}

}
