package hu.webuni.hr.ferencjozsef.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.ferencjozsef.config.HrConfigProperties;
import hu.webuni.hr.ferencjozsef.model.Employee;

@Service
public class DefaultEmployeeService extends BaseEmployeeService {
	
	@Autowired
	HrConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
		return config.getSalary().getDef().getPercent();
	}

}
