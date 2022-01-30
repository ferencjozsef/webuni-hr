package hu.webuni.hr.ferencjozsef.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.ferencjozsef.service.DefaultEmployeeService;
import hu.webuni.hr.ferencjozsef.service.EmployeeService;

@Configuration
@Profile("!smart")
public class SalaryConfiguration {
	
	@Bean
	@Primary
	public EmployeeService employeeService() {
		return new DefaultEmployeeService();
	}
	
}
