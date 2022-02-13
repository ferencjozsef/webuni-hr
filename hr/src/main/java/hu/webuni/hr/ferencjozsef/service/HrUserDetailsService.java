package hu.webuni.hr.ferencjozsef.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import hu.webuni.hr.ferencjozsef.model.Employee;
import hu.webuni.hr.ferencjozsef.model.HrUser;
import hu.webuni.hr.ferencjozsef.repository.EmployeeRepository;

public class HrUserDetailsService implements UserDetailsService {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Employee employee = employeeRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException(username));
		
		return new HrUser(username, employee.getPassword(), Arrays.asList(new SimpleGrantedAuthority("USER")), employee);
	}

}
