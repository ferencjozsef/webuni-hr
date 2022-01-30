package hu.webuni.hr.ferencjozsef.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonCreator;

public class EmployeeDto {

	private Long id;
	@NotEmpty
	private String name;
	@NotNull
	private PositionDto position;
	@Positive
	private int salary;
	@Past
	private LocalDateTime startDate;
	private CompanyDto company;

	public EmployeeDto() {
	}

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public EmployeeDto(Long id, String name, PositionDto position, int salary, LocalDateTime startDate) {
		super();
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.startDate = startDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PositionDto getPosition() {
		return position;
	}

	public void setPosition(PositionDto position) {
		this.position = position;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
		this.company = company;
	}


}
