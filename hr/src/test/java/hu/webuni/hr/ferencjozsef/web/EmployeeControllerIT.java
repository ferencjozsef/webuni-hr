package hu.webuni.hr.ferencjozsef.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.ferencjozsef.dto.EmployeeDto;
import hu.webuni.hr.ferencjozsef.dto.PositionDto;
import hu.webuni.hr.ferencjozsef.mapper.PositionMapper;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;
import hu.webuni.hr.ferencjozsef.repository.PositionRepository;
import hu.webuni.hr.ferencjozsef.service.PositionService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {

	private static final String BASE_URI = "/api/employees";

	@Autowired
	WebTestClient webTestClient;

	@Autowired
	PositionService positionService;
	
	@Autowired
	PositionMapper positionMapper;
	
	@Autowired
	PositionRepository positionRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private String username = "testuser";
	private String pass = "pass";
	
	@BeforeEach
	public void init() {
		employeeRepository.deleteAll();
		positionRepository.deleteAll();
		
		if (employeeRepository.findByUsername(username).isEmpty()) {
			Employee employee = new Employee();
			employee.setUsername(username);
			employee.setPassword(passwordEncoder.encode(pass));
			employeeRepository.save(employee);
		}
	}

	@Test
	void testThatCreatedEmployeeIsListed() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = createEmployeeDto("Kis Elek");

		createEmployee(newEmployee)
			.expectStatus()
			.isOk();

		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter.subList(0, employeesBefore.size()))
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(employeesBefore);

		assertThat(employeesAfter.get(employeesAfter.size() - 1))
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(newEmployee);
	}

	@Test
	void testThatModifyEmployeeIsListed() throws Exception {
		EmployeeDto newEmployee = createEmployeeDto("Kis Elek");
		EmployeeDto savedEmployee = createEmployee(newEmployee)
										.expectBody(EmployeeDto.class)
										.returnResult()
										.getResponseBody();

		List<EmployeeDto> employeesBefore = getAllEmployees();

		savedEmployee.setName("Kis Elek István");
		modifyEmployee(savedEmployee);

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
		assertThat(employeesAfter.get(employeesAfter.size() - 1))
			.usingRecursiveComparison()
			.isEqualTo(savedEmployee);

	}

	@Test
	void testThatCreateEmployeeWithMissingName() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();
		
		EmployeeDto newEmployee = createEmployeeDto(null);
		
		createEmployee(newEmployee)
			.expectStatus()
			.isBadRequest();

		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
	}

	@Test
	void testThatModifyEmployeeWithMissingName() throws Exception {
		EmployeeDto newEmployee = createEmployeeDto("Kiss Iván");
		EmployeeDto savedEmployee = createEmployee(newEmployee)
				.expectStatus()
				.isOk()
				.expectBody(EmployeeDto.class)
				.returnResult()
				.getResponseBody();
				
		List<EmployeeDto> employeesBefore = getAllEmployees();
		
		EmployeeDto invalidEmployee = createEmployeeDto("");
		invalidEmployee.setId(savedEmployee.getId());
		
		modifyEmployee(invalidEmployee)
			.expectStatus()
			.isBadRequest();
		
		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
		assertThat(employeesAfter.get(employeesAfter.size()-1))
			.usingRecursiveComparison()
			.isEqualTo(savedEmployee);
	}

	private List<EmployeeDto> getAllEmployees() {
		List<EmployeeDto> responseList = webTestClient
											.get()
											.uri(BASE_URI)
											.headers(headers -> headers.setBasicAuth(username,pass))
											.exchange()
											.expectStatus()
											.isOk()
											.expectBodyList(EmployeeDto.class)
											.returnResult()
											.getResponseBody();

		// Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		Collections.sort(responseList, Comparator.comparing(EmployeeDto::getId));

		return responseList;
	}

	private ResponseSpec createEmployee(EmployeeDto newEmployeeDto) {
		return webTestClient
				.post()
				.uri(BASE_URI)
				.headers(headers -> headers.setBasicAuth(username,pass))
				.bodyValue(newEmployeeDto)
				.exchange();
	}

	private ResponseSpec modifyEmployee(EmployeeDto employeeDto) {
		return webTestClient
				.put()
				.uri(BASE_URI + "/" + employeeDto.getId())
				.headers(headers -> headers.setBasicAuth(username,pass))
				.bodyValue(employeeDto)
				.exchange();
	}
	
	private EmployeeDto createEmployeeDto(String name) {
		PositionDto positionDto = createPositionDto("Tester", "COLLEGE");
	
		return new EmployeeDto(0L, name, positionDto, 400_000,
				LocalDateTime.of(2020, 10, 11, 12, 0, 0));
	}
	
	private PositionDto createPositionDto(String name, String education) {
		PositionDto positionDto = new PositionDto();
		positionDto.setName(name);
		positionDto.setEducation(education);
		return positionMapper.positionToDto(positionService.save(positionMapper.dtoToPosition(positionDto)));
	}
}
