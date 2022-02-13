package hu.webuni.hr.ferencjozsef.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hu.webuni.hr.ferencjozsef.model.Employee;

@Controller
public class EmployeeTLController {
	
	private List<Employee> allEmployees = new ArrayList<>();
	
	{
		//allEmployees.add(new Employee(1L, "Kiss Endre", "Csoportvezető", 5_000_000, LocalDateTime.of(2020, 10, 1, 12, 0, 0)));
	}
	
	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/employees")
	public String listEmployees(Map<String, Object> model) {
		model.put("employees", allEmployees);
		//model.put("newEmployee", new Employee());
		return "employees";
	}
	
	@PostMapping("/employees")
	public String addEmployee(Employee employee) {
		allEmployees.add(employee);
		return "redirect:employees";
	}
	
	@GetMapping("/employees/{id}")
	public String modifyEmployee(@PathVariable long id, Map<String, Object> model) {
		model.put("employee", allEmployees.stream()
				.filter(e -> e.getId() == id)
				.findFirst()
				.get());
		return "modifyEmployee";
	}
	
	@PostMapping("/updateEmployee")
	public String updateEmployee(Employee employee) {
		for (int i=0; i<allEmployees.size(); i++) {
			if (allEmployees.get(i).getId() == employee.getId()) {
				allEmployees.set(i, employee);
				break;
			}
		}
		return "redirect:employees";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee (@PathVariable long id) {
		for (int i=0; i<allEmployees.size(); i++) {
			if (allEmployees.get(i).getId() == id) {
				allEmployees.remove(i);
				break;
			}
		}
		return "redirect:/employees";
	}
}
