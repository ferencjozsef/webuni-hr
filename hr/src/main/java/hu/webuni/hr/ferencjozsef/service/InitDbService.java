package hu.webuni.hr.ferencjozsef.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.ferencjozsef.model.Company;
import hu.webuni.hr.ferencjozsef.model.CompanyType;
import hu.webuni.hr.ferencjozsef.model.Education;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.model.Position;
import hu.webuni.hr.ferencjozsef.repository.CompanyRepository;
import hu.webuni.hr.ferencjozsef.repository.CompanyTypeRepository;
import hu.webuni.hr.ferencjozsef.repository.DayOffRepository;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;
import hu.webuni.hr.ferencjozsef.repository.PositionDetailsByCompanyRepository;
import hu.webuni.hr.ferencjozsef.repository.PositionRepository;

@Service
public class InitDbService {

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CompanyTypeRepository companyTypeRepository;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
	
	@Autowired
	DayOffRepository dayOffRepository;
	
	@Autowired
	PasswordEncoder  passwordEncoder;
	
	
	public void clearDB() {
		companyTypeRepository.deleteAll();
		positionRepository.deleteAll();
		companyRepository.deleteAll();
		positionDetailsByCompanyRepository.deleteAll();
		employeeRepository.deleteAll();
	}
	
	@Transactional
	public void insertTestData( ) {
		CompanyType companyTypeBt = new CompanyType(null, "Bt.", null);
		companyTypeRepository.save(companyTypeBt);
		CompanyType companyTypeKft = new CompanyType(null, "KFT.", null);
		companyTypeRepository.save(companyTypeKft);
		CompanyType companyTypeZrt = new CompanyType(null, "Zrt.", null);
		companyTypeRepository.save(companyTypeZrt);
		CompanyType companyTypeNyrt = new CompanyType(null, "Nyrt.", null);
		companyTypeRepository.save(companyTypeNyrt);


		Position positionCeo = new Position("Vezérigazgató", Education.UNIVERSITY);
		positionRepository.save(positionCeo);
		Position positionTester = new Position("Tesztelő", Education.COLLEGE);
		positionRepository.save(positionTester);
		Position positionTestManager = new Position("Tesztmenedzser", Education.PHD);
		positionRepository.save(positionTestManager);
		Position positionDeveloper = new Position("Fejlesztő", Education.COLLEGE);
		positionRepository.save(positionDeveloper);
		Position positionLeadDeveloper = new Position("Vezető fejlesztő", Education.UNIVERSITY);
		positionRepository.save(positionLeadDeveloper);
		Position positionNoEducation = new Position("Asszisztens", null);
		positionRepository.save(positionNoEducation);
		
		
		Company companyNoEmployee = new Company(null, 111, "Nincs Dolgozó BT", "2222 Budapest HR utca 11", companyTypeBt, null);
		companyRepository.save(companyNoEmployee);

		
		Employee employeeNotInTheCompany = new Employee(null, "Nincs Cégben", positionCeo, 850_000, LocalDateTime.of(2010, 7, 1, 0, 0, 0), null);
		employeeRepository.save(employeeNotInTheCompany);

		
		Company company1 = new Company(null, 123, "IT HR", "1111 Budapest HR utca 1", companyTypeZrt, null);
		companyRepository.save(company1);

		Employee employee1ToComoapny1 = new Employee(null, "Nagy Elemmér", positionCeo, 950_000, LocalDateTime.of(2010, 7, 1, 0, 0, 0), null);
		employee1ToComoapny1.setCompany(company1);
		employeeRepository.save(employee1ToComoapny1);

		Employee employee2ToComoapny1 = new Employee(null, "Teszt Elek", positionTester, 500_000, LocalDateTime.of(2015, 7, 1, 0, 0, 0), null);
		employee2ToComoapny1.setCompany(company1);
		employeeRepository.save(employee2ToComoapny1);

		Employee employee3ToComoapny1 = new Employee(null, "Teszt Elek 2", positionTester, 485_145, LocalDateTime.of(2015, 7, 1, 0, 0, 0), null);
		employee3ToComoapny1.setCompany(company1);
		employeeRepository.save(employee3ToComoapny1);


		Company company2 = new Company(null, 222, "IT egy dolgozó", "1111 Budapest HR utca 11", companyTypeKft, null);
		companyRepository.save(company2);

		Employee employee1ToComoapny2 = new Employee(null, "Egy Dolgozó",positionTestManager, 450_000, LocalDateTime.of(2010, 7, 1, 0, 0, 0), null);
		employee1ToComoapny2.setCompany(company2);
		employeeRepository.save(employee1ToComoapny2);

		Employee employeeToDayOff = new Employee(null, "Nagy Áron",positionTestManager, 450_000, LocalDateTime.now(), null);
		employeeToDayOff.setUsername("user1");
		employeeToDayOff.setPassword(passwordEncoder.encode("pass1"));
		Employee savedEmloyeeHoliday = employeeRepository.save(employeeToDayOff);
		Employee boss = new Employee(null, "Kiss Géza",positionTestManager, 450_000, LocalDateTime.now(), null);
		boss.setUsername("user2");
		boss.setPassword(passwordEncoder.encode("pass2"));
		Employee savedBoss = employeeRepository.save(boss);
		savedEmloyeeHoliday.setBoss(savedBoss);


		
		
//		Company companyPositions = new Company(null, 222, "IT Poziciók", "1111 Budapest HR utca 11", companyTypeKft, null);
//		
//		PositionDetailsByCompany pdByCompanyTester = new PositionDetailsByCompany();
//		pdByCompanyTester.setCompany(companyPositions);
//		pdByCompanyTester.setMinSalary(450_000);
//		pdByCompanyTester.setPosition(positionTester);
//		positionDetailsByCompanyRepository.save(pdByCompanyTester);
		
	}

}
