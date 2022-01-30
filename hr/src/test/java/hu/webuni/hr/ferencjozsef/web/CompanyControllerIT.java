package hu.webuni.hr.ferencjozsef.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.ferencjozsef.dto.CompanyDto;
import hu.webuni.hr.ferencjozsef.dto.EmployeeDto;
import hu.webuni.hr.ferencjozsef.dto.PositionDto;
import hu.webuni.hr.ferencjozsef.mapper.EmployeeMapper;
import hu.webuni.hr.ferencjozsef.mapper.PositionMapper;
import hu.webuni.hr.ferencjozsef.model.Company;
import hu.webuni.hr.ferencjozsef.model.Education;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.model.Position;
import hu.webuni.hr.ferencjozsef.repository.CompanyRepository;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;
import hu.webuni.hr.ferencjozsef.repository.PositionRepository;
import hu.webuni.hr.ferencjozsef.service.PositionService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompanyControllerIT {
	
	private static final String BASE_URI = "/api/companies";
	
	@Autowired
	WebTestClient webTestClient;

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	PositionRepository positionRepository;
	
//	@Autowired
//	CompanyService companyService;
	
//	@Autowired
//	CompanyMapper companyMapper;

//	@Autowired
//	PositionService positionService;
//	
//	@Autowired
//	PositionMapper positionMapper;
	
	
	// új alkalmazott vehető fel
	//@PostMapping("/{id}/employees")
	@Test
	void testThatAddEmployeeToCompany() throws Exception {
		long companyId = createComapany();
		Optional<Company> companyOptinal = companyRepository.findById(companyId);
		assertThat(companyOptinal).isNotEmpty();
		
		long employeeId = createEmployee("Kiss István");
		Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
		assertThat(employeeOptional).isNotEmpty();
		
		EmployeeDto addEmployee = employeeMapper.employeeToDto(employeeOptional.get());

		addEmployeeToCompany(companyId, addEmployee);
		
		CompanyDto savedComapany = findCompanyById(companyId);
		System.out.println("Hali");
	}
	
	
	
	private ResponseSpec addEmployeeToCompany(long companyId, EmployeeDto newEmployeeDto) {
		String path = BASE_URI + "/" + companyId + "/employees";
		return webTestClient
				.post()
				.uri(path)
				.bodyValue(newEmployeeDto)
				.exchange()
				.expectStatus()
				.isOk();
	}

	private CompanyDto findCompanyById(long companyId) {
		String path = BASE_URI + "/" + companyId;
		return webTestClient
				.get()
				.uri(path)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(CompanyDto.class)
				.returnResult()
				.getResponseBody();
	}

	private long createComapany() {
		return companyRepository.save(new Company(null, 123, "IT company", "1111 Budapest ", null, null)).getId();
	}

	private long createEmployee(String name) {
		Position position = createPosition("tester");
		return employeeRepository.save(new Employee(null, name, position, 300_000, LocalDateTime.now(), null)).getId();
	}
	
	private Position createPosition(String name) {
		return positionRepository.save(new Position(name, Education.COLLEGE));
	}
	
//	
//	private ResponseSpec createEmployee(EmployeeDto newEmployeeDto) {
//		return webTestClient
//				.post()
//				.uri(BASE_URI)
//				.bodyValue(newEmployeeDto)
//				.exchange();
//	}
//	
//	
//	private EmployeeDto createEmployeeDto(String name) {
//		PositionDto positionDto = createPositionDto("Tester", "COLLEGE");
//	
//		return new EmployeeDto(0L, name, positionDto, 400_000,
//				LocalDateTime.of(2020, 10, 11, 12, 0, 0));
//	}
//	
//	private PositionDto createPositionDto(String name, String education) {
//		PositionDto positionDto = new PositionDto();
//		positionDto.setName(name);
//		positionDto.setEducation(education);
//		return positionMapper.positionToDto(positionService.save(positionMapper.dtoToPosition(positionDto)));
//	}
}
