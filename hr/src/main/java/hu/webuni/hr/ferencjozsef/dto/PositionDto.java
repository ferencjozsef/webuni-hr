package hu.webuni.hr.ferencjozsef.dto;

import java.util.ArrayList;
import java.util.List;

public class PositionDto {

	private long id;
	private String name;
	private String education;
	List<EmployeeDto> employees = new ArrayList<>();
	
	public PositionDto() {
		
	}

	public PositionDto(long id, String name, String education, List<EmployeeDto> employees) {
		super();
		this.id = id;
		this.name = name;
		this.education = education;
		this.employees = employees;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}
}
