package hu.webuni.hr.ferencjozsef.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.ferencjozsef.dto.CompanyDto;
import hu.webuni.hr.ferencjozsef.dto.EmployeeDto;
import hu.webuni.hr.ferencjozsef.mapper.EmployeeMapper;
import hu.webuni.hr.ferencjozsef.model.Company;
import hu.webuni.hr.ferencjozsef.model.Education;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.model.Position;
import hu.webuni.hr.ferencjozsef.repository.CompanyRepository;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;
import hu.webuni.hr.ferencjozsef.repository.PositionRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
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
	
	@BeforeEach
	public void inti() {
		employeeRepository.deleteAll();
		companyRepository.deleteAll();
	}
	
	@Test
	void testThatAddEmployeeToCompany() throws Exception {
		long companyId = createComapany();
		
		Optional<Employee> employee = createEmployee("Kiss István");
		
		EmployeeDto addEmployee = employeeMapper.employeeToDto(employee.get());
		
		CompanyDto companyBefor = findCompanyById(companyId);

		addEmployeeToCompany(companyId, addEmployee);
		
		CompanyDto companyAfter = findCompanyById(companyId);

		assertThat(companyAfter.getEmployees().size() - 1)
			.isEqualTo(companyBefor.getEmployees().size());

		assertThat(companyAfter.getEmployees().get(0))
			.usingRecursiveComparison()
			.isEqualTo(addEmployee);
	}
	
	@Test
	void testThatDeleteEmployeeFromCompany() throws Exception {
		long companyId = createComapany();
		Optional<Employee> employee =  createEmployee("Kiss István");
		
		EmployeeDto addEmployee = employeeMapper.employeeToDto(employee.get());
		addEmployeeToCompany(companyId, addEmployee);
		
		CompanyDto companyBefor = findCompanyById(companyId);

		deleteEmployeeToCompany(companyId, employee.get().getId());
		
		CompanyDto companyAfter = findCompanyById(companyId);

		assertThat(companyAfter.getEmployees().size() + 1)
			.isEqualTo(companyBefor.getEmployees().size());

		assertThat(companyAfter.getEmployees())
			.isEmpty();
		
	}

	@Test
	void testThatReplaceAllEmployeesFromCompany() throws Exception {
		long companyId = createComapany();
		Optional<Employee> employee1 = createEmployee("Kiss István 1");
		
		EmployeeDto addEmployee = employeeMapper.employeeToDto(employee1.get());
		addEmployeeToCompany(companyId, addEmployee);
		
		CompanyDto companyBefor = findCompanyById(companyId);

		Optional<Employee> employee2 = createEmployee("Kiss István 2");
		Optional<Employee> employee3 = createEmployee("Kiss István 3");

		List<EmployeeDto> addEmployees = new ArrayList<>();
		addEmployees.add(employeeMapper.employeeToDto(employee2.get()));
		addEmployees.add(employeeMapper.employeeToDto(employee3.get()));
		
		replaceEmployeesFromCompany(companyId, addEmployees);
		
		CompanyDto companyAfter = findCompanyById(companyId);

		assertThat(companyAfter.getEmployees().size() - 1)
			.isEqualTo(companyBefor.getEmployees().size());

		assertThat(companyAfter.getEmployees())
			.usingRecursiveComparison()
			.isEqualTo(addEmployees);

	}
	
	private CompanyDto findCompanyById(long companyId) {
		String path = BASE_URI + "/" + companyId + "?full=true";
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

	private ResponseSpec deleteEmployeeToCompany(long companyId, long employeeId) {
		String path = BASE_URI + "/" + companyId + "/employees/" + employeeId;
		return webTestClient
				.delete()
				.uri(path)
				.exchange()
				.expectStatus()
				.isOk();
	}

	private ResponseSpec replaceEmployeesFromCompany(long companyId, List<EmployeeDto> addEmployees) {
		String path = BASE_URI + "/" + companyId + "/employees";
		return webTestClient
				.put()
				.uri(path)
				.bodyValue(addEmployees)
				.exchange()
				.expectStatus()
				.isOk();
	}	
	
	private long createComapany() {
		Company savedCompany = companyRepository.save(new Company(null, 123, "IT company", "1111 Budapest ", null, null));
		Optional<Company> companyOptinal = companyRepository.findById(savedCompany.getId());
		assertThat(companyOptinal).isNotEmpty();
		return savedCompany.getId();
	}

	private Optional<Employee> createEmployee(String name) {
		Position position = createPosition("tester");
		Employee savedEmployee = employeeRepository.save(new Employee(null, name, position, 300_000, LocalDateTime.now(), null));
		Optional<Employee> employeeOptional = employeeRepository.findById(savedEmployee.getId());
		assertThat(employeeOptional).isNotEmpty();
		return employeeOptional;
	}
	
	private Position createPosition(String name) {
		return positionRepository.save(new Position(name, Education.COLLEGE));
	}

	/*
	private List<CompanyDto> getAllCompanyies() {
		List<CompanyDto> responseList = webTestClient
											.get()
											.uri(BASE_URI + "?full=true")
											.exchange()
											.expectStatus()
											.isOk()
											.expectBodyList(CompanyDto.class)
											.returnResult()
											.getResponseBody();

		Collections.sort(responseList, Comparator.comparing(CompanyDto::getId));

		return responseList;
	}
	*/

}
