package hu.webuni.hr.ferencjozsef.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.ferencjozsef.service.EmployeeService;
import hu.webuni.hr.ferencjozsef.service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartSalaryConfiguration {
	
	@Bean
	public EmployeeService employeeService() {
		return new SmartEmployeeService();
	}
	
}
