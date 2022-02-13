package hu.webuni.hr.ferencjozsef.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.ferencjozsef.dto.DayOffDto;
import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.repository.DayOffRepository;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class DayOffIT {

	private static final String BASE_URI = "/api/dayoffs";
	
	@Autowired
	WebTestClient webTestClient;
	
	@Autowired
	DayOffRepository dayOffRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	private String username1 = "testuser1";
	private String pass1 = "pass1";

	private String username2 = "testuser2";
	private String pass2 = "pass2";

	@BeforeEach
	public void inti() {
		employeeRepository.deleteAll();
		dayOffRepository.deleteAll();
		
		if (employeeRepository.findByUsername(username1).isEmpty()) {
			Employee employee = new Employee();
			employee.setName("develop");
			employee.setUsername(username1);
			employee.setPassword(passwordEncoder.encode(pass1));
			employeeRepository.save(employee);
		}

		if (employeeRepository.findByUsername(username2).isEmpty()) {
			Employee employee = new Employee();
			employee.setName("boss");
			employee.setUsername(username2);
			employee.setPassword(passwordEncoder.encode(pass2));
			employeeRepository.save(employee);
		}

	}

	@Test
	void testThatAddHoliday() throws Exception {
		List<DayOffDto> holidaysBefor = getAllDayOffs(username1, pass1);
		DayOffDto holiday = new DayOffDto();
		holiday.setEmployeeId(employeeRepository.findByUsername(username1).get().getId());
		holiday.setStartDate(LocalDate.now().plusDays(7));
		holiday.setEndDate(LocalDate.now().plusDays(14));
		
		createDayOff(username1, pass1, holiday);
		
		List<DayOffDto> holidaysAfter = getAllDayOffs(username1, pass1);
		
		assertThat(holidaysAfter.subList(0, holidaysBefor.size()))
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(holidaysBefor);

		assertThat(holidaysAfter.get(holidaysAfter.size() - 1))
			.usingRecursiveComparison()
			.ignoringFields("id")
			.ignoringFields("createDate")
			.isEqualTo(holiday);
	}
	
	private List<DayOffDto> getAllDayOffs(String username, String pass) {
		List<DayOffDto> responseList = webTestClient
											.get()
											.uri(BASE_URI)
											.headers(headers -> headers.setBasicAuth(username,pass))
											.exchange()
											.expectStatus()
											.isOk()
											.expectBodyList(DayOffDto.class)
											.returnResult()
											.getResponseBody();

		Collections.sort(responseList, Comparator.comparing(DayOffDto::getId));

		return responseList;
	}
	
	private ResponseSpec createDayOff(String username, String pass, DayOffDto dayOffDto) {
		String path = BASE_URI;
		return webTestClient
				.post()
				.uri(path)
				.headers(headers -> headers.setBasicAuth(username,pass))
				.bodyValue(dayOffDto)
				.exchange()
				.expectStatus()
				.isOk();
	}
}
