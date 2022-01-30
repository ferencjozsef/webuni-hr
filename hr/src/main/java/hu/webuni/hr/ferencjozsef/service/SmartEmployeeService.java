package hu.webuni.hr.ferencjozsef.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.ferencjozsef.config.HrConfigProperties;
import hu.webuni.hr.ferencjozsef.config.HrConfigProperties.Smart;
import hu.webuni.hr.ferencjozsef.model.Employee;

@Service
public class SmartEmployeeService extends BaseEmployeeService {

	@Autowired
	HrConfigProperties config;
	
	@Override
	public int getPayRaisePercent(Employee employee) {
		Double yearsOfWork = ChronoUnit.DAYS.between(employee.getStartDate(), LocalDateTime.now()) / 365.0;
		//System.out.println(yearsOfWork);
		
		Smart smartConfig = config.getSalary().getSmart(); 
		
//		if(yearsOfWork >= smartConfig.getLimit1()) {
//			return smartConfig.getPercent1();
//		} 
//		
//		if (yearsOfWork >= smartConfig.getLimit2()) {
//			return smartConfig.getPercent2();
//		}
//		
//		if (yearsOfWork >= smartConfig.getLimit3()) {
//			return smartConfig.getPercent3();
//		}
//		return 0;
		
		TreeMap<Double, Integer> intervalls = smartConfig.getLimitsAndPercents();
		Integer maxLimit = null;
		for (Entry<Double, Integer> entry: intervalls.entrySet()) {
			if (yearsOfWork >= entry.getKey()) {
				maxLimit = entry.getValue();
			} else {
				break;
			}
		}
		return maxLimit == null ? 0: maxLimit;
		
	}
}
